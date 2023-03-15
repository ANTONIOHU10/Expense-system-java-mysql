package NetWork.Message;

/**
 * a request for all balances
 */
public class ConsultBalanceRequest extends Message{
    public ConsultBalanceRequest() {
        super(MessageType.CONSULTATION_BALANCE_INFORMATION_REQUEST);
    }
}
