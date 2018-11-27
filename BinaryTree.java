import java.util.ArrayList;

/*
Create (i.e., create a data structure with no elements).
        Search
        Insert
        Delete
*/
class Node{

    private
        int v;
        int height;
        Node parent;
        Node leftChi;
        Node rightChi;
    public Node(){}
    public Node(int val){
        this(val, 0, null, null, null);
    }
    public Node(int val, Node p){
        this(val, 0, p, null, null);
    }
    public Node(int val, int height, Node p){
        this(val, height, p, null, null);
    }
    public Node(int val, int height, Node parent, Node leftN, Node rightN){
        this.v = val;
        this.height = height;
        this.parent = parent;
        this.leftChi = leftN;
        this.rightChi = rightN;
    }
    public Node getParent(){return parent;}
    public void setParent(Node p){this.parent = p;}
    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public Node getLeftChi() {
        return leftChi;
    }

    public void setLeftChi(Node leftChi) {
        if(leftChi!=null) leftChi.setParent(this);
        this.leftChi = leftChi;
    }

    public Node getRightChi() {
        return rightChi;
    }

    public void setRightChi(Node rightChi) {
        if(rightChi!=null) rightChi.setParent(this);
        this.rightChi = rightChi;
    }
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
public class BinaryTree {
    private
    long searchRcurCallCnt;
    long rotCallCnt;
    long insertRecurCallCnt;
    long deleteRecurCallCnt;
    long searchMinMaxIterCnt;
    private Node head;
    public BinaryTree(){

        head = null;
        searchRcurCallCnt = 0;
        rotCallCnt = 0;
        insertRecurCallCnt = 0;
        deleteRecurCallCnt = 0;
        searchMinMaxIterCnt = 0;
    }
    public void callStatClear(){
        searchRcurCallCnt = 0;
        rotCallCnt = 0;
        insertRecurCallCnt = 0;
        deleteRecurCallCnt = 0;
        searchMinMaxIterCnt = 0;
    }
    public long callCnt(){
        return searchRcurCallCnt + rotCallCnt + insertRecurCallCnt + deleteRecurCallCnt + searchMinMaxIterCnt;
    }
    public void callStat(){
        System.out.printf("searchRcurCallCnt\n");
        System.out.printf("rotCallCnt(No rot)\n");
        System.out.printf("insertRecurCallCnt\n");
        System.out.printf("deleteRecurCallCnt(call searchRcur and searchMinMax)\n");
        System.out.printf("searchMinMaxIterCnt\n");
        System.out.printf("%d\n",searchRcurCallCnt);
        System.out.printf("%d\n",rotCallCnt);
        System.out.printf("%d\n",insertRecurCallCnt);
        System.out.printf("%d\n",deleteRecurCallCnt);
        System.out.printf("%d\n",searchMinMaxIterCnt);
    }
    public Node search(int v){
        Node nodeV = head;
        while(nodeV!=null){
            searchRcurCallCnt++;
            if(nodeV.getV()<v) nodeV=nodeV.getRightChi();
            else if(nodeV.getV()>v)nodeV=nodeV.getLeftChi();
            else return nodeV;
        }
        return null;
    }
    public Node insert(int v){
        Node nodeV = head;
        if(nodeV==null){
            head = new Node(v);
            return head;
        }
        while(true){
            insertRecurCallCnt++;
            if(nodeV.getV()<v){
                if(nodeV.getRightChi()==null){
                    nodeV.setRightChi(new Node(v, nodeV));
                    return nodeV.getRightChi();
                }
                else nodeV=nodeV.getRightChi();
            }
            else if(nodeV.getV()>v){
                if(nodeV.getLeftChi()==null){
                    nodeV.setLeftChi(new Node(v, nodeV));
                    return nodeV.getLeftChi();
                }
                else nodeV=nodeV.getLeftChi();
            }
            else return null;
        }
    }
    public boolean delete(int v){
        deleteRecurCallCnt++;
        Node nodeV = search(v);
        if(nodeV==null) return false;
        if(nodeV.getLeftChi()==null){
            Node tmpP = nodeV.getParent();
            if(tmpP==null){
                head = nodeV.getRightChi();
                if(head!=null) head.setParent(null);
            }
            else {
                if (tmpP.getLeftChi() == nodeV) {
                    tmpP.setLeftChi(nodeV.getRightChi());
                } else {
                    tmpP.setRightChi(nodeV.getRightChi());
                }
            }
        }else if(nodeV.getRightChi()==null){
            Node tmpP = nodeV.getParent();
            if(tmpP==null){
                head = nodeV.getLeftChi();
                if(head!=null) head.setParent(null);
            }
            else {
                if (tmpP.getLeftChi() == nodeV) {
                    tmpP.setLeftChi(nodeV.getLeftChi());
                } else {
                    tmpP.setRightChi(nodeV.getLeftChi());
                }
            }
        }else {
            Node tmpP = nodeV.getRightChi();
            while (tmpP.getLeftChi() != null){
                searchMinMaxIterCnt++;
                tmpP = tmpP.getLeftChi();
            }
            Node tmpPP = tmpP.getParent();
            if (tmpPP.getLeftChi() == tmpP) {
                tmpPP.setLeftChi(tmpP.getRightChi());
            } else {
                tmpPP.setRightChi(tmpP.getRightChi());
            }
            nodeV.setV(tmpP.getV());
        }

        return true;
    }
    public void inorderPrintTree(){
        inorderTrav(head);
        System.out.println();
    }
    private void inorderTrav(Node root){
        Node tmpP = root;
        if(tmpP==null){
            return;
        }
        else{
            inorderTrav(tmpP.getLeftChi());
            System.out.print(tmpP.getV()+" ");
            inorderTrav(tmpP.getRightChi());
        }
    }
}
