package Methods;

import java.util.Hashtable;
import java.util.Scanner;
import LinkedStack.*;


public class InfixPostFixMethods {

    private LinkedStack stack = new LinkedStack();

    public InfixPostFixMethods(){

    }

    public String infixToPostfix(String infix) {
        if (infix == null)
            throw new IllegalArgumentException("Infix Expression Is Null!");

        if (infix == "")
            return "Empty Infix Expression Error!";

        char[] infixArray = infix.toCharArray();
        if (parenCheck(infixArray,infixArray.length) == false)
            return "Parens Do Not Match Error!";

        String result = "", operand = "";
        infix = infix.replace(" ","");

        int i = 0;
        while (i < infix.length()){

            if (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.' || Character.isLetter(infix.charAt(i))){
                //if the succeeding character  is a letter (and isn't the last character in string) - send an infix error message
                if (i != infix.length()-1 && (Character.isLetter(infix.charAt(i)) && Character.isLetter(infix.charAt(i+1)) || infix.charAt(i+1) == '('))
                    return "Infix Syntax Error!";
                operand += infix.charAt(i);
            }
            else if (infix.charAt(i) == '('){
                //if the succeeding symbol is an operator return an infix syntax error
                if (isOperator(infix.charAt(i)))
                    return "Infix Syntax Error!";
                //if the succeeding symbol is a closing parenthesis, return an empty infix expression error
                if (infix.charAt(i+1) == ')')
                    return "Empty Infix Expression Error!";
                if (!operand.isEmpty()){
                    result += operand + " ";
                    operand = "";
                }

                stack.push(infix.charAt(i));

            }else if (infix.charAt(i) == ')'){

                if (!operand.isEmpty()){
                    result += operand + " ";
                    operand = "";
                }
                while (!stack.top().equals('(')){
                    Object temp = stack.pop();
                    result += temp + " ";
                }
                stack.pop();
            }else{
                //if the succeeding symbol is another operator, a closing parenthesis, return an infix error

                if (i == 0 || i == infix.length()-1 ||
                        isOperator(infix.charAt(i+1)) || infix.charAt(i+1) == ')')
                    return "Infix Syntax Error!";

                if (!operand.isEmpty()){
                    result += operand + " ";
                    operand = "";
                }

                while (!stack.isEmpty()  && onStackItemVal(stack.top().toString()) > curItemVal(infix.charAt(i))) {
                    Object temp = stack.pop();
                    result += temp + " ";
                }

                stack.push(infix.charAt(i));
            }
            i++;
        }
        //if we reach the end of the infix expression, and the operand variable isn't empty, append it to the postfix result
        if (!operand.isEmpty()){
            result += operand + " ";
            operand = "";
        }

        while (!stack.isEmpty()) {
            //extreme edge case: if the top of the stack is a parenthesis, pop and throw away, otherwise, pop and append to result
            if (stack.top().equals('(') || stack.top().equals(')'))
                stack.pop();
            else {
                Object temp = stack.pop();
                result += temp + " ";
            }
        }

        return result;
    }

    public String postfixEvaluation(String postfix, Hashtable keys){

        String result;
        int i = 0;
        String operand = "";

        while(i < postfix.length()){

            if (Character.isDigit(postfix.charAt(i)) || postfix.charAt(i) == '.' ){
                operand += postfix.charAt(i); //add the character to our operand string
            }
            else if (Character.isLetter(postfix.charAt(i))){

                if (keys != null && keys.containsKey(postfix.charAt(i))){
                    stack.push(keys.get(postfix.charAt(i)));
                }
            }
            else if (postfix.charAt(i) == ' ') { //space indicates the end of an operand

                if (!operand.isEmpty()) {
                    stack.push(operand);
                    operand = "";
                }
            }
            else if (isOperator(postfix.charAt(i))) {

                if (stack.size() >= 2) {//only calculate if there are at least 2 in stack (one for left and right)
                    double right = Double.valueOf(stack.pop().toString());
                    double left = Double.valueOf(stack.pop().toString());
                    stack.push(operandEvaluation(postfix.charAt(i), left, right));
                }
            }
            i++;

        }

        if (stack.size() == 1){
            result = stack.pop().toString();
        }else{
            return "Postfix Syntax Error!";
        }

        return result;
    }

    public void returnOperand(String operand,String result){
        if (!operand.isEmpty()){
            result += operand + " ";
            operand = "";
        }
    }

    public boolean parenCheck(char array[], int n){
        LinkedStack S = new LinkedStack();

        for(int i = 0; i < n;i++){
            if (array[i] == '(' || array[i] == '{' || array[i] == '['){
                S.push(array[i]);

            }else if (array[i] == ')' || array[i] == '}' || array[i] == ']'){
                if (S.isEmpty())
                    return false; //nothing to match with

                Object pop = S.pop();
                if ((char)pop != '(' && (char)pop != '{' && (char)pop != '['  )
                    return false; //wrong type
            }
        }

        if (S.isEmpty())
            return true;
        else
            return false;
    }

    public int onStackItemVal(String item){
        int val = 0;
        switch(item){
            case "(":
                val = 0;
                break;
            case "^":
                val = 5;
                break;
            case "*":
            case "/":
            case "%":
                val = 4;
                break;
            case "+":
            case "-":
                val = 2;
                break;
        }

        return val;
    }

    public int curItemVal(char item){
        int val = 0;
        switch(item){
            case '(':
                val = 100;
                break;
            case ')':
                val = 0;
                break;
            case '^':
                val = 6;
                break;
            case '*':
            case '/':
            case '%':
                val = 3;
                break;
            case '+':
            case '-':
                val = 1;
                break;
        }
        return val;
    }

    public boolean isOperator (char op){
        switch(op){
            case '+':
            case '*':
            case '^':
            case '/':
            case '-':
            case '%':
                return true;
        }
        return false;
    }

    public double operandEvaluation(char item, double left, double right){
        switch(item){
            case '+':
                return left + right;
            case '-':
                return left - right;
            case '/':
                return left / right;
            case '*':
                return left * right;
            case '^':
                return Math.pow(left,right);
            case '%':
                return left % right;

        }
        return 0;
    }
}
