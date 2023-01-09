
import java.util.ArrayList;
import java.time.Duration;
import java.time.Instant;

public class Solve{
    public static boolean valid(int p,int x, int y, int[][] board) {
        if(p==0)  return false;
        for (int i = 0; i < 9; i += 1) {
            if (board[i][y] == p && i != x) {
                return true;
            }
            if (board[x][i] == p && i != y) {
                return true;
            }
        }
        return false;
    }

    public static boolean check(Point target, int[][] board, int num, ArrayList<Integer> group[]) {
        for (int i = 0; i < 9; i += 1) {
            if (board[i][target.y] == num && i != target.x) {
                return true;
            }
            if (board[target.x][i] == num && i != target.y) {
                return true;
            }
        }

        for (int i = 0; i < group[target.id].size(); i++) {
            if (num == group[target.id].get(i)) {
                return true;
            }
        }
        return false;
    }

    public static void groupAll(int board[][], ArrayList<Integer> group[]) {
        for (int row = 0; row < 9; row += 1) {
            for (int col = 0; col < 9; col += 1) {
                if (board[row][col] != 0) {
                    group[(row / 3) * 3 + (col / 3)].add(board[row][col]);
                }
            }
        }
    }

    public static ArrayList<Point> empty(int board[][], ArrayList<Integer> group[]) {

        ArrayList<Point> pointList = new ArrayList<Point>();
        for (int i = 0; i < 9; i += 1) {
            for (int j = 0; j < 9; j += 1) {
                if (board[i][j] == 0) {
                    Point p = new Point();
                    p.x=i;
                    p.y=j;
                    p.id=(i / 3) * 3 + (j / 3);

                    for (int k = 1; k <= 9; k += 1) {
                        if (!check(p, board, k, group)) {
                            p.possible.add(k);
                        }
                    }
                    pointList.add(p);
                }
            }
        }

        return pointList;
    }

    public static void dfs(Point p, int board[][], ArrayList<Point> points,  ArrayList<Integer> group[], int solution[][]) {

        for (int i=0; i<p.possible.size(); i++) {

            int v = p.possible.get(i);
            p.value = v;
            if (!check(p, board, v, group)) {
                board[p.x][p.y] = v;

                if (points.size() <= 0) {
                    for (int r = 0; r < 9; r += 1) {
                        for (int c = 0; c < 9; c += 1) {
                            solution[r][c] = board[r][c];
                        }
                    }
                    return;
                }

                Point p2 = points.get(0);
                points.remove(0);
                dfs(p2, board, points, group, solution);
                points.add(p2);
                p2.value = 0;
                board[p.x][p.y] = 0;
                board[p2.x][p2.y] = 0;
            }
        }
    }

    public static void  log(int solution[][]) {
        for (int r = 0; r < 9; r += 1) {
            for (int c = 0; c < 9; c += 1) {
                System.out.print(solution[r][c] );
            }
            System.out.println();
        }

    }

    public static int solve(int board[][], ArrayList<Integer> group[], int solution[][]) {
        long start = System.nanoTime();
        groupAll(board, group);
        ArrayList<Point> points = empty(board, group);
        int cnt = points.size();
        Point p = points.get(0);
        points.remove(0);
        dfs(p, board, points, group, solution);
        return cnt;
    }

    public static void findOne(String command, int solution[][], int board[][]) {

        int x = (command.charAt(5))-'0';
        int y = (command.charAt(7))-'0';

        if (x > 9 || y > 9 || x < 1 || y < 1) {
            System.out.println("Invalid command!");
            return;
        }

        System.out.println("The answer here is:" + solution[x - 1][y - 1]);
        board[x - 1][y - 1] = solution[x - 1][y - 1];
        log(board);
    }

    public static int checkOne(String command, int solution[][], int board[][]) {

        int x = command.charAt(6)-'0';
        int y =command.charAt(8)-'0';
        int v = command.charAt(10)-'0';

        if (x > 9 || y > 9 || x < 1 || y < 1 || v < 1 || v > 9) {
            System.out.println("Invalid command!");
            return 0;
        }
        if (solution[x - 1][y - 1] == v) {
            System.out.println("Your answer is correct!");
            board[x - 1][y - 1] = solution[x - 1][y - 1];
            log(board);
            return 1;
        } else {
            System.out.println("Your answer is wrong!");
            return 0;
        }
    }




}

