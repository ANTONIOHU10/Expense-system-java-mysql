package NetWork.Message;

import Model.Balance;
import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.List;

public class ConsultBalanceResponse extends Message{
    List<Balance> listOfBalance = new ArrayList<>();
    public ConsultBalanceResponse(List<Balance> listOfBalance) {
        super(MessageType.CONSULTATION_BALANCE_INFORMATION_RESPONSE);
        this.listOfBalance = listOfBalance;
    }

    public List<Balance> getListOfBalance() {
        return listOfBalance;
    }
}
