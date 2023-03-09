package NetWork.Message;

public enum MessageType {
    LOGIN,
    LOGIN_RESPONSE,
    LOGOUT,
    //un messaggio generale da inviare, una frase
    MESSAGE,
    //un messaggio che chiede un certo oggetto da restituire, esempio una spesa
    DATA_REQUEST_MESSAGE,
    //risposta di un oggetto generale
    DATA_REPLY_MESSAGE,
    DISCONNECT,
    EXPENSE,
    ERROR
}
