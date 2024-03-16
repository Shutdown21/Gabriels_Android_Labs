package algonquin.cst2335.hube0078;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.hube0078.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.hube0078.databinding.ReceiveMessageBinding;
import algonquin.cst2335.hube0078.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages;
    ChatRoomViewModel chatModel;
    private RecyclerView.Adapter myAdapter;
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
    String currentDateAndTime = sdf.format(new Date());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if(messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
        }

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.sendButton.setOnClickListener(click->{
            messages.add(new ChatMessage(binding.textInput.getText().toString(),currentDateAndTime,true));
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
        });
        binding.receiveButton.setOnClickListener(click->{
            messages.add(new ChatMessage(binding.textInput.getText().toString(),currentDateAndTime,false));
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view;
                if (viewType == 0) {
                    view = inflater.inflate(R.layout.sent_message, parent, false);
                } else {
                    view = inflater.inflate(R.layout.receive_message, parent, false);
                }
                return new MyRowHolder(view);

            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage obj = messages.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(obj.getTimeSent());

            }

            @Override
            public int getItemCount() {
                return messages.size();
            }
            @Override
            public int getItemViewType(int position){
                ChatMessage message = messages.get(position);
                if (message.IsSentButton()){
                    return 0;
                } else {
                    return 1;
                }

            }
        });
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }
    public class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }
}
