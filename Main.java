import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

public class Main {

    public static void main(String[] args) {
        int in[]={10, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000};


        int maxRptRnd = 100;

        for (int testRnd = 0; testRnd < in.length; testRnd++) {
            int testSizeIn = in[testRnd];
            double timeInRpt_bt = 0;
            double timeInRpt_avlt = 0;
            double timeInRpt_splt = 0;
            double timeInRpt_trp = 0;
            for(int repeatRnd = 1; repeatRnd < maxRptRnd; repeatRnd++) {

                List<Integer> eleListIn = new ArrayList<Integer>();


                int tmpNum;

                for (int i = 0; i < testSizeIn; i++) {
                    tmpNum = (int) (Math.random() * Integer.MAX_VALUE);
                    eleListIn.add(tmpNum);
                }
                // increasing Order
                // sort(eleListIn);


                // the num to be inserted/deleted/searched (for insert)
                // tmpNum = (int) (Math.random() * Integer.MAX_VALUE);

                // a num already inserted (for delete or search)
                tmpNum = eleListIn.get( (int) (Math.random() * testSizeIn));



                //***********************************************************************
                //***********************************************************************
                long before;

                // Bnry tree
                //System.out.println("Binary Tree:\n");
                BinaryTree bt = new BinaryTree();
                for (int i = 0; i < testSizeIn; i++) {
                    bt.insert(eleListIn.get(i));
                }
                before = bt.callCnt();
                bt.search(tmpNum);
                long timeIn_bt = bt.callCnt() - before;
                timeInRpt_bt = timeInRpt_bt + timeIn_bt;


                //***********************************************************************
                // AVL tree
                //System.out.println("\nAVL Tree:\n");
                AVLTree avlt = new AVLTree();
                for (int i = 0; i < testSizeIn; i++) {
                    avlt.insert(eleListIn.get(i));
                }

                before = avlt.callCnt();
                avlt.search(tmpNum);
                long timeIn_avlt = avlt.callCnt() - before;
                timeInRpt_avlt = timeInRpt_avlt + timeIn_avlt;



                //***********************************************************************
                // Splay Tree

                //System.out.println("\nSplay Tree:\n");
                SplayTree splt = new SplayTree();
                for (int i = 0; i < testSizeIn; i++) {
                    splt.insert(eleListIn.get(i));
                }

                before = splt.callCnt();
                splt.search(tmpNum);
                long timeIn_splt = splt.callCnt() - before;
                timeInRpt_splt = timeInRpt_splt + timeIn_splt;


                //***********************************************************************
                // Treap

                //System.out.println("\nTreap:\n");
                Treap trp = new Treap();
                for (int i = 0; i < testSizeIn; i++) {
                    trp.insert(eleListIn.get(i));
                }

                before = trp.callCnt();
                trp.search(tmpNum);
                long timeIn_trp = trp.callCnt() - before;
                timeInRpt_trp = timeInRpt_trp + timeIn_trp;

            }
            // function calls statics
            System.out.printf("[create empty, insert %d; then insert one random] repeat %d times; timePerInsertAverage\n", testSizeIn, maxRptRnd);
            System.out.printf("%f\n", timeInRpt_bt / maxRptRnd);
            System.out.printf("%f\n", timeInRpt_avlt / maxRptRnd);
            System.out.printf("%f\n", timeInRpt_splt / maxRptRnd);
            System.out.printf("%f\n", timeInRpt_trp / maxRptRnd);
        }
    }
}
