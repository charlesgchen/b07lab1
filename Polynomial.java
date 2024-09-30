
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Polynomial{
    public double[] coefficents;
    public int[] exponents;
    public Polynomial(){
        coefficents = new double[1];
        coefficents[0] = 0;
        exponents = new int[1];
        exponents[0] = 0;
    }
    public Polynomial(double [] co_entries, int [] exp_entries){
        coefficents = new double[co_entries.length];
        exponents = new int[exp_entries.length];
        for(int i = 0; i < co_entries.length; i++){
            coefficents[i] = co_entries[i];
            exponents[i] = exp_entries[i];
        }
    }
    public Polynomial(File input) throws FileNotFoundException{
        Scanner poly = new Scanner(input);
        String text = poly.nextLine();
        poly.close();
        String[] equation = text.split("(?=+)|(?=-)");


        //if first element is negative;
        if("".compareto(equation[0])){
            coefficents = new double[equation.length - 1];
            exponents = new int[equation.length - 1];
            for(int i = 0; i < equation.length - 1; i++){
                String [] term = equation[i + 1].split("x");
                coefficents[i] = Double.parseDouble(term[0]);
                exponents[i] = Integer.parseInt(term[1]);
            }
        }
        //if first element is positive:
        else{
            coefficents = new double[equation.length];
            exponents = new int[equation.length];
            for(int i = 0; i < equation.length; i++){
                String [] term = equation[i].split("x");
                coefficents[i] = Double.parseDouble(term[0]);
                exponents[i] = Integer.parseInt(term[1]);
            }
        }
    }   
    //returns index of exponent in exponent array if it exists, -1 if it doesn't
    private int exponentExists(int exp, int[] exp2){
        for(int j = 0; j < exp2.length; j++){
            if(exp == exponents[j]){
                return j;
            }
        }
        return -1;
    }
    private double[] increaseLength(double[] arr){
        double[] copy = new double[arr.length + 1];
        System.arraycopy(arr, 0, copy, 0, arr.length);
        return copy;
    }
    private int[] increaseLength(int[] arr){
        int[] copy = new int[arr.length + 1];
        System.arraycopy(arr, 0, copy, 0, arr.length);
        return copy;
    }
    public Polynomial add(Polynomial f2){
        int f2_size = f2.coefficents.length;
        int f1_size = coefficents.length;
        double[] coef_copy;
        int[] exp_copy;
        int k;
    
        exp_copy = coefficents.clone();
        coef_copy = exponents.clone();
        //add the smaller array to the bigger one
        for(int i = 0; i < f2_size; i++){
            //check if exponent exists
            k = f2.exponentExists(f2[i], exp_copy);
            if(k != -1){
                coef_copy[k] += f2.coefficents[i];
            }
            //insert coefficient if it doesn't exist
            else{
                coef_copy = this.increaseLength(coef_copy);
                exp_copy = this.increaseLength(exp_copy);
                coef_copy[coef_copy.length] = f2.coefficents[i];
                exp_copy[exp_copy.length] = f2.exponents[i];
            }
            
        }
        return this(coef_copy, exp_copy);
    }
        
    
    public Polynomial multiply(Polynomial f2){
        //use two arrays, iterate for every term in polynomial f1
        double[] coef_sum = new double[coefficents.length];
        Arrays.fill(coef_sum, 0);
        double[] exp_sum = new double[coefficents.length];
        Arrays.fill(exp_sum, 0);
        Polynomial sum = new Polynomial(coef_sum, exp_sum);
        for(int i = 0; i < coefficents.length; i++){
            double[] coef_copy = new double[coefficents.length];
            Arrays.fill(coef_copy, 0);
            double[] exp_copy = new double[coefficents.length];
            Arrays.fill(exp_copy, 0);
            for(int k = 0; k < f2.coefficents.length; k++){
                coef_copy[k] = coefficents[i] * f2.coefficents[k];
                exp_copy[k] = exponents[i] + f2.exponents[k];
            }
            sum = sum.add(new Polynomial(coef_copy, exp_copy));
        }
        return sum;
    }
    public void printTerms(){
        for(int i = 0; i < coefficents.length; i++){
            System.out.println("terms " + i);
            System.out.println(coefficents[i] + " " + exponents[i]);
        }
    }
    public double evaluate(double input){
        double sum = 0;
        for(int i = 0; i < coefficents.length; i++){
            sum += coefficents[i] * Math.pow(input, exponents[i]);
        }
        return sum;
    }
    public boolean hasRoot(double input){
        double sum = this.evaluate(input);
        if(sum != 0) return false;
        return true;
    }
    public void saveToFile(String fileLoc){
        FileWriter fw = new FileWriter(fileLoc);
        for(int i = 0; i < coefficents.length; i++){
            fw.write(String.valueOf(coefficents[i]));
            fw.write(String.valueOf(exponents[i]));
        }
        fw.close();
    }

}
