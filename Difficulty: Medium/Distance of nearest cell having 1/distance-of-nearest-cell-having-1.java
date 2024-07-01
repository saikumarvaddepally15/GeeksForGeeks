//{ Driver Code Starts
import java.util.*;
import java.lang.*;
import java.io.*;
class GFG
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        while(T-->0)
        {
            String[] s = br.readLine().trim().split(" ");
            int n = Integer.parseInt(s[0]);
            int m = Integer.parseInt(s[1]);
            int[][] grid = new int[n][m];
            for(int i = 0; i < n; i++){
                String[] S = br.readLine().trim().split(" ");
                for(int j = 0; j < m; j++){
                    grid[i][j] = Integer.parseInt(S[j]);
                }
            }
            Solution obj = new Solution();
            int[][] ans = obj.nearest(grid);
            for(int i = 0; i < ans.length; i++){
                for(int j = 0; j < ans[i].length; j++){
                    System.out.print(ans[i][j] + " ");
                }
                System.out.println();
            }
        }
    }
}
// } Driver Code Ends


    class Tuple{
        int first;
        int second;
        int third;
        public Tuple(int first,int second,int third){
            this.first=first;
            this.second=second;
            this.third=third;
        }
    }

class Solution
{
    //Function to find distance of nearest 1 in the grid for each cell.
    public int[][] nearest(int[][] mat)
    {
        
       int row=mat.length;
        int col=mat[0].length;
        int vis[][]=new int[row][col];
        int dist[][]=new int[row][col];
        Queue<Tuple> q=new LinkedList<>();
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(mat[i][j]==1){
                    q.add(new Tuple(i,j,0));
                    vis[i][j]=1;
                }
                else{
                    vis[i][j]=0;
                }
            }
        }
        int[] drow={-1,0,1,0};
        int[] dcol={0,+1,0,-1};

        while(!q.isEmpty()){
            int r=q.peek().first;
            int c=q.peek().second;
            int steps=q.peek().third;
            q.remove();
            dist[r][c]=steps;

            for(int i=0;i<4;i++){
                int nr=r+drow[i];
                int nc=c+dcol[i];
                if(nr>=0 && nr<row && nc>=0 && nc<col && vis[nr][nc]==0){
                    vis[nr][nc]=1;
                    q.offer(new Tuple(nr,nc,steps+1));
                }
            }


        }

        return dist;
    }
}