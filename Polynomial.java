public class Polynomial{
    public double[] coefficents;
    public Polynomial(){
        coefficents = new double[1];
        coefficents[0] = 0;
    }
    public Polynomial(double [] entries){
        coefficents = new double[entries.length];
        for(int i = 0; i < entries.length; i++){
            coefficents[i] = entries[i];
        }
    }
    public Polynomial add(Polynomial f2){
        int f2_size = f2.coefficents.length;
        int f1_size = coefficents.length;
        double[] copy;
        if(f1_size >= f2_size){
            copy = new double[f1_size];
            copy = coefficents.clone();
            for(int i = 0; i < f2_size; i++){
                copy[i] += f2.coefficents[i];
            }
        }
        else{
            copy = new double[f2_size];
            copy = f2.coefficents.clone();
            for(int i = 0; i < f1_size; i++){
                copy[i] += coefficents[i];
            }
        }
        return new Polynomial(copy);
    }
    public void printCoefficents(){
        for(int i = 0; i < coefficents.length; i++){
            System.out.println("Coefficients:");
            System.out.println(coefficents[i] + "\n");
        }
    }
    public double evaluate(double input){
        double sum = 0;
        for(int i = 0; i < coefficents.length; i++){
            sum += coefficents[i] * Math.pow(input, i + 1);
        }
        return sum;
    }
    public boolean hasRoot(double input){
        double sum = this.evaluate(input);
        if(sum != 0) return false;
        return true;
    }
}
