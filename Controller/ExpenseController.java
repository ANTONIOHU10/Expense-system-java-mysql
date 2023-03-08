package Controller;

import Model.Expense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseController {
    //es: ogni persona viene associata con tutte le spese
    private Map<String, List<Expense>> expensesPerPerson;

    public ExpenseController() {
        this.expensesPerPerson = new HashMap<>();
    }

    /*
    * "Davide" presenta una spesa da pagare in equa distribuzione per tutte le altre persone
    * quindi devo calcolare l'importo da pagare per tutte le altre persone a Davide
    * */
    public void addExpense(Expense expense) {
        String payer = expense.getPayerID();
        //computeIfAbsent-> if the key does not exist, add this key, cioè se non "Davide" ha ancora la lista, ne crea una
        //le spese di una persona, cioè la lista che viene associata al nome del pagante
        List<Expense> allExpenses = expensesPerPerson.computeIfAbsent(payer, k -> new ArrayList<>());
        //aggiungere la spesa che ha pagato Davide
        allExpenses.add(expense);
        //calcolare il rimborso da effettuare a Davide dagli altri
        double value = expense.getValue();
        //nella mappa>
            //Davide -  lista
            //Luigi - lista
            //...
        int numberPersons = expensesPerPerson.size();

        //importo da pagare per le altre persone
        double valuePerPersona = value / numberPersons;

        //.keySet = restituisce l'insieme (implementato come un oggetto Set) di tutte le chiavi della mappa
        //ovvero i nomi di tutte le persone coinquiline dell'appartamento
        for (String person : expensesPerPerson.keySet()) {
            //(value)= nome della persona
            if (!person.equals(value)) {
                List<Expense> expenseOfAPerson = expensesPerPerson.get(person);
                expenseOfAPerson.add(new Expense(expense.getDescription(), value, payer));
            }
        }
    }

    //calcola la differenza tra: l'importo da pagare- spesa che ha effettuato
    public double saldo(String person) {

        //ottenere la lista dei pagamenti di una persona
        List<Expense> expensePerson = expensesPerPerson.getOrDefault(person, new ArrayList<>());

        //il totale delle spese sostenute dal gruppo
        double totalExpense = expensePerson.stream().mapToDouble(Expense::getValue).sum();

        //l'importo che ogni persona dovrebbe aver pagato se tutte le spese fossero state equamente divise tra i partecipanti.
        //cioè la spesa pagata dagli altri, quindi Davide deve rimborsare queste spese
        double totalValuePerPerson = totalExpense / expensesPerPerson.size();

        //totale di spese che ha pagato, quindi è da togliere dal pagamento totale da effettuare
        //rappresenta il totale delle spese sostenute da ogni singola persona all'interno del gruppo.
        double totalExpensePerPerson = expensesPerPerson.values().stream()
                .flatMap(List::stream)
                .filter(spesa -> !spesa.getPayerID().equals(person))
                .mapToDouble(Expense::getValue)
                .sum();

        //
        return totalValuePerPerson * expensesPerPerson.size() - totalExpensePerPerson;
    }
}
