public class Sorter {
    public static int [] bubbleSort(int [] input){
        long starttime=System.currentTimeMillis();
        boolean swapped;
        int iterations=0;
        do {
            swapped=false;
            iterations++;
            for(int i=0;i<input.length-1;i++){
                if(input[i+1]<input[i]){
                    int less=input[i+1];
                    input[i+1]=input[i];
                    input[i]=less;
                    swapped=true;
                }
            }
        }while (swapped);
        System.out.println("iterations:"+iterations+";time:"+(System.currentTimeMillis()-starttime));
        return input;
    }
    public static int [] insertionSort(int [] input){
        long starttime=System.currentTimeMillis();
        int iterations=0;
        int sortedPart=1;
        while (sortedPart!=input.length) {
            int i=sortedPart;
            while (i>0&&input[i] < input[i - 1]) {
                int less=input[i];
                input[i]=input[i-1];
                input[i-1]=less;
                i--;
            }
            sortedPart++;
        }
        System.out.println("iterations:"+sortedPart+";time:"+(System.currentTimeMillis()-starttime));
        return input;
    }
    static int increment(Integer m){
        m++;
        return m;
    }
}
