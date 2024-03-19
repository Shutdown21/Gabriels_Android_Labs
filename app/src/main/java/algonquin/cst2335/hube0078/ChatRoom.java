package algonquin.cst2335.hube0078;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch( item.getItemId() )
        {
            case R.id.item_1:

                //put your ChatMessage deletion code here. If you select this item, you should show the alert dialog
                //asking if the user wants to delete this message.
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        ChatMessageDAO mDAO = db.cmDAO();


        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if(messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database

                runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter ));
            });
        }

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);

        binding.sendButton.setOnClickListener(click->{
            String messageText = binding.textInput.getText().toString();
            ChatMessage message = new ChatMessage(messageText, currentDateAndTime, true);
            messages.add(message);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                mDAO.insertMessage(message);
            });
        });
        binding.receiveButton.setOnClickListener(click->{
            String messageText = binding.textInput.getText().toString();
            ChatMessage message = new ChatMessage(messageText, currentDateAndTime, false);
            messages.add(message);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                mDAO.insertMessage(message);
            });
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
            ChatMessageDAO mDao;
            itemView.setOnClickListener(clk ->{
                int position = getAbsoluteAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setMessage("Do you want to delete this message: " + messageText.getText())
                    .setTitle("Question:")
                    .setNegativeButton("No", (dialog, cl) -> {})
                    .setPositiveButton("Yes", (dialog, cl) -> {
                        ChatMessage removedMessage = messages.get(position);
                        messages.remove(position);
                        myAdapter.notifyItemRemoved(position);

                        Snackbar.make(messageText, "You deleted a message #"+position, Snackbar.LENGTH_LONG)
                                .setAction("Undo", click ->{
                                    messages.add(position, removedMessage);
                                    myAdapter.notifyItemInserted(position);
                                })
                                .show();
                    })
                    .create().show();
            });

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }
}
