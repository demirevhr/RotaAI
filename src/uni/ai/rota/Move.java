package uni.ai.rota;

class Move {
    private static int MIN_POSITION = 0;
    private static int MAX_POSITION = 8;
    private static int MIDDLE_POSITION = 4;
    private static int NUM_NEIGHBOURS = 3;

    private static int[][] NEIGHBOURS = {
            {4, 2, 1},
            {4, 3, 0},
            {4, 5, 0},
            {4, 6, 1},
            {0, 1, 2, 3, 5, 6, 7, 8},
            {4, 7, 2},
            {4, 8, 3},
            {4, 8, 5},
            {4, 7, 6}
    };

    private int from;
    private int to;
    Move() {this.from = -1; this.to = -1;}
    Move(int from, int to) {this.from = from; this.to = to;}
    int getFrom() {return from;}
    int getTo() {return to;}

    boolean isValid() {
        if (!(from >= MIN_POSITION && from <= MAX_POSITION && to >= MIN_POSITION && to <= MAX_POSITION)) {
            return false;
        }

        if (from == to || from == MIDDLE_POSITION || to == MIDDLE_POSITION) {
            return true;
        }

        for(int i = 0; i < NUM_NEIGHBOURS; ++i) {
            if (NEIGHBOURS[from][i] == to) {
                return true;
            }
        }
        return false;
    }

    static int[] getNeighbours(int pos) {
        return NEIGHBOURS[pos];
    }
}

