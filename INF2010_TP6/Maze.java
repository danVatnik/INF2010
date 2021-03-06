import java.util.*;

// Classe a remplir pour realiser le TP en utilisant les classes fournies

public class Maze{

	public Maze(int width, int height, int seed){
		
		this.width = width;
		this.height = height;

		// Initialisation du labyrinthe avec tous les murs
		walls = new ArrayList<Wall>();
		for(int i = 0; i < height; ++i){
			for(int j = 0; j < width; ++j){
				if(i > 0){
					walls.add(new Wall(j+i*height, j+(i-1)*height));
				}
				if(j > 0){
					walls.add(new Wall(j+i*height, j-1+i*height));
				}
			}
		}

		// Creation du graphe modelisant le labyrinthe
		// Le graphe est decrit par une liste d'adjascence
		mazeGraph = new MazeGraph(height, width);
		
		// On permute les murs de maniere aleatoire
		generator = new Random(seed);
		for(int i = 0; i < walls.size(); ++i){
			// nombre aleatoire ne depassant pas maze.size()-1
            int j = generator.nextInt(walls.size());
            
            //on altere le maze a l'index aleatoire avec celui de l'index du for
            if( i != j ){
                Wall tempWall = walls.get(j);
                walls.set(j, walls.get(i) );
                walls.set(i, tempWall);	
            }
		}
		
		// Initialisation des structures annexes
		ds = new DisjointSet(width*height);
		path = new ArrayList<Integer>();
	}

	public void generate(){
		
		for(int index = walls.size() - 1; index >= 0; --index)
		{
			Wall mur = walls.get(index);
			if(!ds.areInSameSet(mur.room1, mur.room2))
			{
				walls.remove(index);
				ds.union(mur.room1, mur.room2);
				mazeGraph.connect(mur.room1, mur.room2);
			}
		}
	}

	public void solve(){
		// verifie si il y a un chemin possible
        if(!ds.areInSameSet(0, height*width-1)){
            return;
        }
        
        // Creer une file et ajouter le debut
        Queue<Room> q = new LinkedList<Room>();
        Room room = mazeGraph.getStart();
        room.distance = 0;
        q.add(room);
        
        while(!q.isEmpty())
        {
        	Room piece = q.poll();
        	for(Iterator<Room> voisins = piece.neighboors.iterator(); voisins.hasNext();)
            {
        		Room unVoisin = voisins.next();
            	if(unVoisin.distance == -1)
            	{
            		unVoisin.distance = piece.distance + 1;
            		unVoisin.source = piece;
            		q.add(unVoisin);
            	}
            }
        }
        
        Room roomToStart = mazeGraph.getFinish();
        do
        {
        	path.add(roomToStart.id);
        	roomToStart = roomToStart.source;
        }
        while(roomToStart != room);
        path.add(roomToStart.id);
	}

	public class Wall{
		
		public Wall(int r1, int r2){
			room1 = r1;
			room2 = r2;
		}

		public int room1;
		public int room2;
	}
	
	public class MazeGraph{
		
		public MazeGraph(int height, int width){
			adjacencyList = new ArrayList<Room>();
			for(int i = 0; i < height*width; ++i)
				adjacencyList.add(new Room(i));
			nbRooms = height*width;
		}
		public int nbRooms;
		ArrayList<Room> adjacencyList;
		
		public void connect(int room1, int room2){
			if(room1 <0 || room1 >=nbRooms) return;
			if(room2 <0 || room2 >=nbRooms) return;
			if(room1  == room2) return;
			Room r1 = adjacencyList.get(room1);
			Room r2 = adjacencyList.get(room2);
			r1.addNeighboor(r2);
			r2.addNeighboor(r1);
		}
		public Room getStart(){
			if(nbRooms == 0) return null;
			return adjacencyList.get(0);
		}
		public Room getFinish(){
			if(nbRooms == 0) return null;
			return adjacencyList.get(adjacencyList.size()-1);
		}
	}

	public class Room{
		
		public Room(int i){
			id = i;
			distance = -1;
			source = null;
			neighboors = new ArrayList<Room>();
		}

		public int id;
		ArrayList<Room> neighboors;
		int distance;
		Room source;
		
		public void addNeighboor(Room room){
			if( !isNeighboor(room) )
				neighboors.add(room);
		}
		public boolean isNeighboor(Room room){
			return neighboors.contains(room);
		}

	}

	public ArrayList<Wall> walls;
	public MazeGraph mazeGraph;
	public ArrayList<Integer> path;

	public int height;
	public int width;
	public Random generator;
	public DisjointSet ds;
}

