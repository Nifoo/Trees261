class AVLNode{
    public
        int val;
        AVLNode left;
        AVLNode right;
        int height;
    public AVLNode(int val){
        this(val, null, null);
    }
    public AVLNode(int val, AVLNode ln, AVLNode rn){
        this.val = val;
        this.left = ln;
        this.right = rn;
        this.height = 0;
    }
}
public class AVLTree{
    private
    long searchRcurCallCnt;
    long rotCallCnt;
    long insertRecurCallCnt;
    long deleteRecurCallCnt;
    long searchMinMaxIterCnt;
    private AVLNode root;
    public AVLTree(){

        root = null;
        searchRcurCallCnt = 0;
        rotCallCnt = 0;
        insertRecurCallCnt = 0;
        deleteRecurCallCnt = 0;
        searchMinMaxIterCnt = 0;
    }
    public void callStatClear() {
        searchRcurCallCnt = 0;
        rotCallCnt = 0;
        insertRecurCallCnt = 0;
        deleteRecurCallCnt = 0;
        searchMinMaxIterCnt = 0;
    }
    public long callCnt(){
        return searchRcurCallCnt + rotCallCnt+ insertRecurCallCnt + deleteRecurCallCnt + searchMinMaxIterCnt;
    }
    public void callStat(){
        System.out.printf("searchRcurCallCnt\n");
        System.out.printf("rotCallCnt\n");
        System.out.printf("insertRecurCallCnt\n");
        System.out.printf("deleteRecurCallCnt\n");
        System.out.printf("searchMinMaxIterCnt\n");
        System.out.printf("%d\n",searchRcurCallCnt);
        System.out.printf("%d\n",rotCallCnt);
        System.out.printf("%d\n",insertRecurCallCnt);
        System.out.printf("%d\n",deleteRecurCallCnt);
        System.out.printf("%d\n",searchMinMaxIterCnt);
    }
    public boolean isEmpty(){
        return root==null;
    }

    public AVLNode search(int val){
        return searchRcur(val, root);
    }
    private AVLNode searchRcur(int val, AVLNode rt){
        searchRcurCallCnt++;
        if(rt==null) return null;
        if(val<rt.val) return searchRcur(val, rt.left);
        else if(val>rt.val) return searchRcur(val, rt.right);
        else return rt;
    }
    private int height(AVLNode rt){
        if(rt==null) return 0;
        else return rt.height;
    }
    private AVLNode rotLL(AVLNode rt){
        rotCallCnt++;
        AVLNode rt1 = rt.left;
        rt.left = rt1.right;
        rt1.right = rt;
        rt1.height = Math.max(height(rt1.left), height(rt1.right))+1;
        rt.height = Math.max(height(rt.left), height(rt.right))+1;
        return rt1;
    }
    private AVLNode rotRR(AVLNode rt){
        rotCallCnt++;
        AVLNode rt1 = rt.right;
        rt.right = rt1.left;
        rt1.left = rt;
        rt1.height = Math.max(height(rt1.left), height(rt1.right))+1;
        rt.height = Math.max(height(rt.left), height(rt.right))+1;
        return rt1;
    }
    private AVLNode rotLR(AVLNode rt){
        rt.left = rotRR(rt.left);
        return rotLL(rt);
    }
    private AVLNode rotRL(AVLNode rt){
        rt.right = rotLL(rt.right);
        return rotRR(rt);
    }
    public void insert(int[] val){
        for(int i=0; i<val.length; i++) insert(val[i]);
    }
    public void insert(int val){
        root = insertRcur(val, root);
    }

    private AVLNode insertRcur(int val, AVLNode rt){
        insertRecurCallCnt++;
        if(rt==null) return new AVLNode(val, null, null);
        if(val<rt.val) {
            rt.left = insertRcur(val, rt.left);
            if (height(rt.left) - height(rt.right) == 2) {
                if (val < rt.left.val) {
                    rt = rotLL(rt);
                } else {
                    rt = rotLR(rt);
                }
            }
        }
        else if(val>rt.val){
            rt.right = insertRcur(val, rt.right);
            if(height(rt.right)-height(rt.left)==2){
                if(val > rt.right.val){
                    rt = rotRR(rt);
                }
                else{
                    rt = rotRL(rt);
                }
            }
        }
        rt.height = Math.max(height(rt.left), height(rt.right))+1;
        return rt;
    }

    public AVLNode searchMax(AVLNode rt){
        AVLNode tmpr = rt;
        while(tmpr.right!=null){
            searchMinMaxIterCnt++;
            tmpr = tmpr.right;
        }
        return tmpr;
    }
    public AVLNode searchMin(AVLNode rt){
        AVLNode tmpr = rt;
        while(tmpr.left!=null){
            searchMinMaxIterCnt++;
            tmpr = tmpr.left;
        }
        return tmpr;
    }
    public boolean delete(int[] val){
        boolean flagRes = true;
        for(int i=0; i<val.length; i++){
            flagRes = flagRes && delete(val[i]);
        }
        return flagRes;
    }
    public boolean delete(int val){
        if(search(val)==null) return false;
        else{
            root = deleteRcur(val, root);
            return true;
        }
    }
    private AVLNode deleteRcur(int val, AVLNode rt){
        deleteRecurCallCnt++;
        if(rt==null) return null;
        if(val>rt.val) {
            rt.right = deleteRcur(val, rt.right);
            if (height(rt.left) - height(rt.right) == 2) {
                AVLNode tmpr = rt.left;
                if (height(tmpr.left) > height(tmpr.right)) {
                    rt = rotLL(rt);
                } else {
                    rt = rotLR(rt);
                }
            }
        }
        else if(val<rt.val){
            rt.left = deleteRcur(val, rt.left);
            if(height(rt.right) - height(rt.left)==2){
                AVLNode tmpr = rt.right;
                if(height(tmpr.left) < height(tmpr.right)){
                    rt = rotRR(rt);
                }
                else{
                    rt = rotRL(rt);
                }
            }
        }
        // the current node to be deleted
        else{
            if((rt.left!=null) && (rt.right!=null)) {
                // if left subtree higher than right subtree, then find the leftMax as the new rt
                if (height(rt.left) > height(rt.right)) {
                    AVLNode tmprt = searchMax(rt.left);
                    rt.val = tmprt.val;
                    rt.left = deleteRcur(tmprt.val, rt.left);
                } else {
                    AVLNode tmprt = searchMin(rt.right);
                    rt.val = tmprt.val;
                    rt.right = deleteRcur(tmprt.val, rt.right);
                }
            }
            else{
                if(rt.left==null){
                    rt = rt.right;
                }
                else{
                    rt = rt.left;
                }
            }
        }
        if(rt!=null) rt.height = Math.max(height(rt.left), height(rt.right));
        return rt;
    }
    public void inorderPrintTree(){
        inorderTrav(root);
        System.out.println();
    }
    private void inorderTrav(AVLNode root){
        AVLNode tmpP = root;
        if(tmpP==null){
            return;
        }
        else{
            inorderTrav(tmpP.left);
            System.out.print(tmpP.val+" ");
            inorderTrav(tmpP.right);
        }
    }
}