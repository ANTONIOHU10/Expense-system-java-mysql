package NetWork.Message;

import Model.Balance;
import java.util.List;

public class ConsultBalanceResponse extends Message{
    List<Balance> listOfBalance;

    /**
     *
     * @param listOfBalance list of Balance to be sent to Client = result of the consultation
     */
    public ConsultBalanceResponse(List<Balance> listOfBalance) {
        super(MessageType.CONSULTATION_BALANCE_INFORMATION_RESPONSE);
        this.listOfBalance = listOfBalance;
    }

    /**
     *
     * @return list of Balance
     */
    public List<Balance> getListOfBalance() {
        return listOfBalance;
    }
}
