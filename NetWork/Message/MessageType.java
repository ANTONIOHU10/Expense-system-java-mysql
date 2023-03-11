package NetWork.Message;

public enum MessageType {
    LOGIN_REQUEST,//usato
    LOGIN_RESPONSE,//usato
    REGISTER_REQUEST,//usato
    REGISTER_RESPONSE,//usato
    LOGOUT,//usato
    //un messaggio generale da inviare, una frase
    MESSAGE,
    //un messaggio che chiede un certo oggetto da restituire, esempio una spesa
    DATA_REQUEST,
    //risposta di un oggetto generale
    DATA_RESPONSE,
    DISCONNECT,
    EXPENSE,//usato
    ERROR,
    VIEW_USERNAMES_REQUEST,//usato
    VIEW_USERNAMES_RESPONSE//usato
}
