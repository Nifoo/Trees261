class TrpNode{
    public
    int val;
    TrpNode left;
    TrpNode right;
    TrpNode parent;
    double prio;
    public TrpNode(){};
    public TrpNode(int val){
        this.val = val;
        left = null;
        right = null;
        parent = null;
        prio = Math.random();
    }
    public void setLChi(int val){
        this.left = new TrpNode(val);
        this.left.parent = this;
    }
    public void setRChi(int val){
        this.right = new TrpNode(val);
        this.right.parent = this;
    }
    public void setLChi(TrpNode rtl){
        this.left = rtl;
        if(rtl!=null) this.left.parent = this;
    }
    public void setRChi(TrpNode rtr){
        this.right = rtr;
        if(rtr!=null) this.right.parent = this;
    }
}
public class Treap {
    private TrpNode root;
    private
    long searchRcurCallCnt;
    long rotIterCallCnt;
    long insertRecurCallCnt;
    long deleteRecurCallCnt;
    long searchMinMaxIterCnt;
    public Treap(){
        root = null;
        searchRcurCallCnt = 0;
        rotIterCallCnt = 0;
        insertRecurCallCnt = 0;
        deleteRecurCallCnt = 0;
        searchMinMaxIterCnt = 0;
    }
    public void callStatClear() {
        searchRcurCallCnt = 0;
        rotIterCallCnt = 0;
        insertRecurCallCnt = 0;
        deleteRecurCallCnt = 0;
        searchMinMaxIterCnt = 0;
    }

    public long callCnt(){
        return searchRcurCallCnt + rotIterCallCnt  + insertRecurCallCnt  + deleteRecurCallCnt  +  searchMinMaxIterCnt;

    }
    public void callStat(){
        System.out.printf("searchRcurCallCnt\n");
        System.out.printf("rotIterCallCnt\n");
        System.out.printf("insertRecurCallCnt\n");
        System.out.printf("deleteRecurCallCnt\n");
        System.out.printf("searchMinMaxIterCnt\n");
        System.out.printf("%d\n",searchRcurCallCnt);
        System.out.printf("%d\n",rotIterCallCnt);
        System.out.printf("%d\n",insertRecurCallCnt);
        System.out.printf("%d\n",deleteRecurCallCnt);
        System.out.printf("%d\n",searchMinMaxIterCnt);
    }
    private TrpNode rotR(TrpNode rt){
        rotIterCallCnt++;
        //System.out.printf("rotRight @ %d", rt.val);
        //inorderPrintTree();
        TrpNode rt1 = rt.left;
        rt1.parent = rt.parent;
        if(rt1.parent==null) root=rt1;
        else if(rt.parent.left==rt) rt.parent.setLChi(rt1);
        else rt.parent.setRChi(rt1);
        rt.setLChi(rt1.right);
        rt1.setRChi(rt);
        return rt1;
    }
    private TrpNode rotL(TrpNode rt){
        rotIterCallCnt++;
        //System.out.printf("rotLeft @ %d", rt.val);
        //inorderPrintTree();
        TrpNode rt1 = rt.right;
        rt1.parent = rt.parent;
        if(rt1.parent==null) root=rt1;
        else if(rt.parent.left==rt) rt.parent.setLChi(rt1);
        else rt.parent.setRChi(rt1);
        //System.out.printf("%d, %d\n", rt1.val, rt.val);
        rt.setRChi(rt1.left);
        rt1.setLChi(rt);
        return rt1;
    }
    public TrpNode search(int val){
        TrpNode rt = root;
        while(rt!=null && rt.val!=val){
            searchRcurCallCnt++;
            if(val<rt.val) rt=rt.left;
            else rt=rt.right;
        }
        if(rt==null) return null;
        else return rt;
    }
    public void insert(int val){
        if(root==null){
            root = new TrpNode(val);
            return;
        }
        TrpNode rt = root;
        while(true){
            insertRecurCallCnt++;
            if(val<rt.val){
                if(rt.left==null){
                    rt.setLChi(val);
                    rt = rt.left;
                    break;
                }
                else rt=rt.left;
            }
            else if(val>rt.val){
                if(rt.right==null){
                    rt.setRChi(val);
                    rt = rt.right;
                    break;
                }
                else rt=rt.right;
            }
            else return;
        }

        //System.out.printf("after normal Insert:\n");
        //inorderPrintTree();
        TrpNode rtpr = rt.parent;
        // rotate till root
        while(rtpr!=null && rt.prio > rtpr.prio){
            if(rtpr.left == rt){
                //System.out.printf("rt(LC), rtpr %d, %d\n", rt.val, rtpr.val);
                rt = rotR(rtpr);
                rtpr = rt.parent;
            }
            else{
                //System.out.printf("rt(RC), rtpr %d, %d\n", rt.val, rtpr.val);
                rt = rotL(rtpr);
                rtpr = rt.parent;
            }
        }
        return;

    }
    public void delete(int val){
        TrpNode rt = search(val);
        if(rt==null) return;
        // rotate nodeVal to leaf (SiftDown)
        while( !(rt.left==null && rt.right==null) ){
            deleteRecurCallCnt++;
            if(rt.left==null){
                rotL(rt);
            }
            else if(rt.right==null){
                rotR(rt);
            }
            else if(rt.left.prio > rt.right.prio){
                rotR(rt);
            }
            else{
                rotL(rt);
            }
        }
        TrpNode rtpr = rt.parent;
        if(rtpr==null) root=null;
        else if(rtpr.left==rt) rtpr.left=null;
        else rtpr.right=null;
    }
    public void inorderPrintTree(){
        inorderTrav(root);
        System.out.println();
    }
    private void inorderTrav(TrpNode root){
        TrpNode tmpP = root;
        if(tmpP!=null){
            inorderTrav(tmpP.left);
            System.out.print(tmpP.val+" ");
            inorderTrav(tmpP.right);
        }
    }
}
