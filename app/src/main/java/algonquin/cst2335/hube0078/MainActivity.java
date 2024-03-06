package algonquin.cst2335.hube0078;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 *@author Gabriel Hubert
 *@version 1.0
 */

public class MainActivity extends AppCompatActivity {

    /**This holds the text at the centre of the screen*/
    private TextView tv=null;
    /**This holds the text of the button*/
    private Button btn=null;
    /**This holds the text of the EditText*/
    private EditText et=null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.textView);
        btn=findViewById(R.id.button);
        et=findViewById(R.id.editText);

        btn.setOnClickListener(clk->{
            String password=et.getText().toString();

            checkPasswordComplexity(password);
            if(checkPasswordComplexity(password)){
                tv.setText("Your password meets the requirements");
            } else {
                tv.setText("You shall not pass!");
            }

        });
    }


    /**This function checks the complexity of the password.
     *
     *@param pw The String object that we are 1checking
     *@return Returns true if the password is complex.
     */
    boolean checkPasswordComplexity(String pw){
        boolean foundUpperCase,foundLowerCase,foundNumber,foundSpecial;
        foundUpperCase=foundLowerCase=foundNumber=foundSpecial=false;

        String loop=pw;
        for(int i=0;i<loop.length();i++){
            char c=loop.charAt(i);
            if(Character.isDigit(c)){
                foundNumber=true;
            }else if(Character.isUpperCase(c)){
                foundUpperCase=true;
            }else if(Character.isLowerCase(c)){
                foundLowerCase=true;
            }else if(isSpecialCharacter(c)){
                foundSpecial=true;
            }
        }
        if(!foundUpperCase){
            Toast.makeText(this,"Upper case letter missing",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(!foundLowerCase)
        {
            Toast.makeText(this,"Lower case letter missing",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(!foundNumber){
            Toast.makeText(this,"Number character missing",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(!foundSpecial){
            Toast.makeText(this,"Special character missing",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            tv.setText("Your password meets the requirements");
            return true;
    }

    /**This function checks if the character is a special character.
     *
     *@param c The character being checked.
     *@return Returns true if character is a special character.
     */
    boolean isSpecialCharacter(char c){
        switch(c){
            case'#':
            case'$':
            case'%':
            case'^':
            case'*':
            case'!':
            case'@':
            case'?':
                return true;
            default:
                return false;
        }
    }

}