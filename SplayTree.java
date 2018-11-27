class SplNode{
    public
    int val;
    SplNode left;
    SplNode right;
    public SplNode(int val){
        this(val, null, null);
    }
    public SplNode(int val, SplNode ln, SplNode rn){
        this.val = val;
        this.left = ln;
        this.right = rn;
    }
}
public class SplayTree {
    private SplNode root;
    private
    long searchRcurCallCnt;
    long splayIterCallCnt;
    long insertRecurCallCnt;
    long deleteRecurCallCnt;
    long searchMinMaxIterCnt;
    public SplayTree() {
        root = null;
        searchRcurCallCnt = 0;
        splayIterCallCnt = 0;
        insertRecurCallCnt = 0;
        deleteRecurCallCnt = 0;
        searchMinMaxIterCnt = 0;
    }
    public void callStatClear() {
        searchRcurCallCnt = 0;
        splayIterCallCnt = 0;
        insertRecurCallCnt = 0;
        deleteRecurCallCnt = 0;
        searchMinMaxIterCnt = 0;
    }
    public long callCnt(){
        return searchRcurCallCnt + splayIterCallCnt + insertRecurCallCnt + deleteRecurCallCnt + searchMinMaxIterCnt ;
    }
    public void callStat(){
        System.out.printf("searchRcurCallCnt\n");
        System.out.printf("splayIterCallCnt\n");
        System.out.printf("insertRecurCallCnt\n");
        System.out.printf("deleteRecurCallCnt\n");
        System.out.printf("searchMinMaxIterCnt\n");
        System.out.printf("%d\n",searchRcurCallCnt);
        System.out.printf("%d\n",splayIterCallCnt);
        System.out.printf("%d\n",insertRecurCallCnt);
        System.out.printf("%d\n",deleteRecurCallCnt);
        System.out.printf("%d\n",searchMinMaxIterCnt);
    }
    private void splay(int val){
        root = splay(root, val);
    }
    private void rotLChi(SplNode grandp, SplNode parent){
        grandp.left = parent.right;
        parent.right = grandp;
        //split the parent with middle tree       
        parent.left = null;
    }
    private void rotRChi(SplNode grandp, SplNode parent){
        grandp.right = parent.left;
        parent.left = grandp;
        //split the parent with middle tree
        parent.right = null;
    }
    // top-down splay
    private SplNode splay(SplNode rt, int val) {
        if (rt == null) return null;
        SplNode pseuNode = new SplNode(Integer.MAX_VALUE);
        SplNode leftMax = pseuNode;
        SplNode rightMin = pseuNode;
        SplNode t = rt;


        while (true) {
            splayIterCallCnt++;
            if (val == t.val) {
                break;
            }
            else if (val < t.val) {
                //Note: the variable parent is target's parent, the variable t is target's grandp
                SplNode parent = t.left;
                if (parent == null) {
                    break;
                } else {
                    if (val < parent.val) {
                        if (parent.left == null) {
                            //zig
                            t.left = null;
                            rightMin.left = t;
                            rightMin = t;
                            t = parent;
                        } else {
                            //zig-zig
                            SplNode tmp = parent.left;
                            rotLChi(t, parent);
                            //update right tree and its min node
                            rightMin.left = parent;
                            rightMin = parent;
                            //update the middle tree's root
                            t = tmp;
                        }
                    } else {
                        //zig or zig-zag(simplified to zig)
                        t.left = null;
                        rightMin.left = t;
                        rightMin = t;
                        t = parent;
                    }
                }
            }
            else {
                //val>t.val
                SplNode parent = t.right;
                if (parent == null) {
                    break;
                } else {
                    if (val > parent.val) {
                        if (parent.right == null) {
                            //zag
                            t.right = null;
                            leftMax.right = t;
                            leftMax = t;
                            t = parent;
                        } else {
                            //zag-zag
                            SplNode tmp = parent.right;
                            rotRChi(t, parent);
                            //update left tree and its max node
                            leftMax.right = parent;
                            leftMax = parent;
                            //update the middle tree's root
                            t = tmp;
                        }
                    } else {
                        //val <= t.right.val
                        //zag or zag-zig (simplified to zag)
                        t.right = null;
                        leftMax.right = t;
                        leftMax = t;
                        t = parent;
                    }
                }
            }
        }
        //re-assemble
        leftMax.right = t.left;
        rightMin.left = t.right;
        t.left = pseuNode.right;
        t.right = pseuNode.left;
        return t;
    }

    public SplNode search(int val){
        if(root==null) return null;
        splay(val);
        return root;
    }

    public void insert(int[] valL){
        for(int i=0; i<valL.length; i++) insert(valL[i]);
    }
    public void insert(int val){
        root = insertRecur(val, root);
        splay(val);
    }
    private SplNode insertRecur(int val, SplNode rt){
        /*
        insertRecurCallCnt++;
        if(rt==null){
            rt = new SplNode(val, null, null);
            return rt;
        }
        if(rt.val==val) return rt;
        else if(rt.val<val){
            rt.right = insertRecur(val, rt.right);
            return rt;
        }
        else {
            rt.left = insertRecur(val, rt.left);
            return rt;
        }
        */


        if(rt==null ) return new SplNode(val, null, null);
        while(true){
            insertRecurCallCnt++;
            if(rt.val==val) break;
            else if(rt.val<val){
                if(rt.right==null){
                    rt.right = new SplNode(val, null, null);
                    break;
                }
                else rt=rt.right;
            }
            else{
                if(rt.left==null){
                    rt.left = new SplNode(val, null, null);
                    break;
                }
                else rt=rt.left;
            }
        }

        return root;
    }
    public void delete(int[] val){
        for(int i=0; i<val.length; i++) delete(val[i]);
    }
    public void delete(int val){
        root = deleteNormal(root, val);
        splay(val);
    }
    private SplNode searchMin(SplNode rt){
        if(rt==null) return null;
        while(rt.left!=null) rt=rt.left;
        return rt;
    }
    // just delete w/o splay
    private SplNode deleteNormal(SplNode rt, int val){
        deleteRecurCallCnt++;
        if(rt==null) return null;
        if(rt.val==val) {
            if (rt.left == null) return rt.right;
            if (rt.right == null) return rt.left;
            SplNode tmprt = searchMin(rt.right);
            rt.val = tmprt.val;
            rt.right = deleteNormal(rt.right, tmprt.val);
            return rt;
        }
        else if(rt.val < val){
            rt.right = deleteNormal(rt.right, val);
            return rt;
        }
        else{
            rt.left = deleteNormal(rt.left, val);
            return rt;
        }
    }
    public void inorderPrintTree(){
        inorderTrav(root);
        System.out.println();
    }
    private void inorderTrav(SplNode root){
        SplNode tmpP = root;
        if(tmpP!=null){
            inorderTrav(tmpP.left);
            System.out.print(tmpP.val+" ");
            inorderTrav(tmpP.right);
        }
    }
}
