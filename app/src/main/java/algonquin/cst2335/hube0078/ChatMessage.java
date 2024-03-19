package algonquin.cst2335.hube0078;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {
    @ColumnInfo(name="message")
    protected String message;
    @ColumnInfo(name="TimeSent")
    protected String timeSent;
    @ColumnInfo(name = "SendOrReceive")
    protected boolean isSentButton;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    ChatMessage(String m, String t, boolean sent)
    {
        message = m;
        timeSent = t;
        isSentButton = sent;
    }

    public ChatMessage() {

    }

    public String getMessage() {
        return message;
    }
    public String getTimeSent() {
        return timeSent;
    }
    public boolean IsSentButton(){
        return isSentButton;
     }
}
