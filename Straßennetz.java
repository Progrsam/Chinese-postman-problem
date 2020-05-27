//Written by Samuel Schneider, Maximilian Schneider, Jean-Pascal, Markus Helmerking
import abiturklassen_2018.graphklassen.*;
import abiturklassen_2018.linear.*;
public class Straßennetz extends Graph{
    int anzahlKnoten;

    public Straßennetz(){

        addVertex(new Vertex("A"));
        addVertex(new Vertex("B"));
        addVertex(new Vertex("C"));
        addVertex(new Vertex("D"));
        addVertex(new Vertex("E"));
        addVertex(new Vertex("F"));
        addVertex(new Vertex("G"));
        addVertex(new Vertex("H"));
        addVertex(new Vertex("I"));

        addEdge(new Edge(getVertex("A"), getVertex("B"), 3));
        addEdge(new Edge(getVertex("A"), getVertex("C"), 4));
        addEdge(new Edge(getVertex("A"), getVertex("H"), 1));
        addEdge(new Edge(getVertex("B"), getVertex("C"), 2));
        addEdge(new Edge(getVertex("C"), getVertex("D"), 2));
        addEdge(new Edge(getVertex("C"), getVertex("I"), 2));
        addEdge(new Edge(getVertex("C"), getVertex("G"), 5));
        addEdge(new Edge(getVertex("D"), getVertex("I"), 1));
        addEdge(new Edge(getVertex("I"), getVertex("G"), 4));
        addEdge(new Edge(getVertex("I"), getVertex("F"), 3));
        addEdge(new Edge(getVertex("I"), getVertex("E"), 2));
        addEdge(new Edge(getVertex("E"), getVertex("F"), 2));
        addEdge(new Edge(getVertex("F"), getVertex("G"), 1));
        addEdge(new Edge(getVertex("G"), getVertex("H"), 2));
        
        Lösung();

    }

    public List<Vertex> makiereUngeradeKnoten(Vertex start) 
    {
        List<Vertex> l = new List<Vertex>();
        Stack<Vertex> s = new Stack<Vertex>();
        s.push(start);
        start.setMark(true);
        while (!s.isEmpty()) {
            Vertex aktknoten = (s.top());
            List<Vertex> neighbours = getNeighbours(aktknoten);

            neighbours.toFirst();
            while (neighbours.hasAccess() && (neighbours.getContent()).isMarked()) {
                neighbours.next();
            }
            if (!neighbours.hasAccess()) {

                neighbours.toFirst();
                anzahlKnoten = 0;
                while(neighbours.hasAccess()){
                    anzahlKnoten++;
                    neighbours.next();
                }
                if(anzahlKnoten % 2 != 0){
                    if(aktknoten.isMarked()){
                        l.append(aktknoten);
                    }
                }
                s.pop();

            } else {
                Vertex nachbarknoten = neighbours.getContent();
                nachbarknoten.setMark(true);
                s.push(nachbarknoten);
            }
        }

        return l;
    } 

    public double[][] ermittleWeg(){

        double[][] dist = erstelleMatrix(this.getVertices());
        for (int k = 0; k < dist.length; k++)
            for (int j = 0; j < dist.length; j++)
                for (int i = 0; i < dist.length; i++)
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }

        System.out.println("\n" + "kürzeste Verbindung zu jedem Knoten:" + "\n");

        List<Vertex> l = this.getVertices();
        int ll = 0;
        l.toFirst();
        while(l.hasAccess()){
            ll++;
            l.next();
        }
        l.toFirst();

        String[] nodes = new String[ll]; int z = 0;
        while(l.hasAccess()){
            List<Vertex> l1 = this.getNeighbours(l.getContent());
            l1.toFirst();
            while(l1.hasAccess()){
                l1.next();
            }
            nodes[z] = l.getContent().getID();
            l.next();z++;
        }
        
        System.out.print("  ");
        for(int k = 0; k<ll;k++){
            System.out.print(nodes[k] + "   ");
        }
        System.out.println("");
        for(int i = 0; i<ll;i++){
            System.out.print(nodes[i]+ " ");
            for(int j = 0; j<ll;j++){
                System.out.print(dist[i][j]+  " ");
            }
            System.out.println("");
        }

        return dist;
    }

    public double[][] erstelleMatrix(List<Vertex> pListe){     
        List<Vertex> l = pListe;

        int ll = 0;
        l.toFirst();
        while(l.hasAccess()){
            ll++;
            l.next();
        }
        l.toFirst();

        String[] nodes = new String[ll]; int z = 0;
        while(l.hasAccess()){
            List<Vertex> l1 = this.getNeighbours(l.getContent());
            System.out.print("#"+l.getContent().getID() + " Nachbarn: ");
            l1.toFirst();
            while(l1.hasAccess()){
                System.out.print(l1.getContent().getID() + " , ");
                l1.next();
            }
            System.out.println("");
            nodes[z] = l.getContent().getID();
            l.next();z++;
        }
        System.out.println("\n" + "Matrix:" + "\n");
        double[][] Matrix = new double[ll][ll];
        System.out.print("  ");
        for(int k = 0; k<ll;k++){
            System.out.print(nodes[k] + "   ");
        }
        System.out.println("");
        for(int i = 0; i<ll;i++){
            System.out.print(nodes[i]+ " ");
            for(int j = 0; j<ll;j++){

                if(i == j ||this.getEdge(this.getVertex(nodes[i]),
                    this.getVertex(nodes[j]))==null){
                    Matrix[i][j] = Integer.MAX_VALUE;
                }
                else{
                    Matrix[i][j] = this.getEdge(this.getVertex(nodes[i]),this.getVertex(nodes[j])).getWeight();
                }
                if(Matrix[i][j] == Integer.MAX_VALUE){
                    System.out.print("∞" + "   ");
                }
                else{
                    System.out.print(Matrix[i][j] + " ");
                }
            }
            System.out.println("");
        }

        return Matrix;

    }

    public void Lösung(){
        List<Vertex> ls = makiereUngeradeKnoten(getVertex("A"));

        ls.toFirst();
        int anzahlUngeradeKnoten = 0;
        System.out.print("Alle Knoten mit ungeradem Kantengrad: ");
        while(ls.hasAccess()){
            System.out.print(ls.getContent().getID() + " ");
            anzahlUngeradeKnoten++;
            ls.next();
        }
        System.out.println("\n");

        double[][] kwall = ermittleWeg();
        List<Vertex> lgesamt = this.getVertices();
        lgesamt.toFirst();
        ls.toFirst();

        int c = 0;
        List<Integer> sw = new List<Integer>();
        while(ls.hasAccess()){
            c = 0;
            while(lgesamt.hasAccess()){
                c++;
                if(lgesamt.getContent().getID().equals(ls.getContent().getID())){
                    sw.append(c-1);
                }
                lgesamt.next();
            }
            lgesamt.toFirst();
            ls.next();
        }

        ls.toFirst();
        sw.toFirst();

        int gK = Integer.MAX_VALUE;
        int tempgK = 0;
        int[] ver= new int[anzahlUngeradeKnoten];
        int[] tempver = new int[anzahlUngeradeKnoten];

        List<Integer> localList = new List<Integer>();
        sw.toFirst();
        while(sw.hasAccess()){
            localList.append(sw.getContent());
            sw.next();
        }

        for(int c1 = 0;c1<anzahlUngeradeKnoten-1;c1++){
            for(int c2 = 0;c2<anzahlUngeradeKnoten-2;c2++){
                localList.toFirst();
                for (int c3 = 0;c3<c1;c3++)localList.next();
                int tmp = localList.getContent();
                int c4 = 0;
                tempver[c4] = tmp;
                c4++;
                localList.remove();

                localList.toFirst();
                for (int c3 = 0;c3<c2;c3++)localList.next();
                tempgK +=  kwall[tmp][localList.getContent()];
                tempver[c4] = localList.getContent();
                c4++;
                localList.remove();

                while(!localList.isEmpty()){
                    if(localList.hasAccess())localList.toFirst();
                    tmp = localList.getContent();
                    tempver[c4] = tmp;
                    c4++;
                    localList.remove();
                    if(localList.hasAccess())localList.toFirst();
                    tempgK +=  kwall[tmp][localList.getContent()];
                    tempver[c4] = localList.getContent();
                    c4++;
                    localList.remove();
                }

                if(tempgK < gK){
                    gK = tempgK;
                    ver = tempver;
                }
                tempgK = 0;

                sw.toFirst();
                while(sw.hasAccess()){
                    localList.append(sw.getContent());
                    sw.next();
                }
            }
        }

        System.out.println("\n" + "Neue zusätzliche Kantenverbindungen:" + "\n");
        int c5 = 0;
        int c6 = 0;
        lgesamt.toFirst();
        while(lgesamt.hasAccess() && c5 < ver.length){
            if(ver[c5] == c6){
                System.out.print(lgesamt.getContent().getID());

                if(c5 % 2 == 0){
                    System.out.print(" - ");
                }
                else {
                    System.out.println();
                }
                c5++;
                lgesamt.toFirst();
                c6 = 0;
            }
            else{
                lgesamt.next();
                c6++;
            }

        }
        System.out.println("\n" + "Kürzester zugefügter Weg: " + gK);
    }
    
    public static void main(String[] args){
        new Straßennetz();
    }

}

