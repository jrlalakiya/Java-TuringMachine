import javax.swing.JTextArea;

public class TuringMachine {
    private int[][] tuples;
    private int state;
    private int tapeIndex;
    private char[] tape;
    private JTextArea console;

    public TuringMachine(String tmModel, String tape, JTextArea consoleOutput) {
    	String[] tuplesString = tmModel.split(" ");
        tuples = new int[tuplesString.length][5];
        for (int i = 0; i < tuplesString.length; i++) {
            String tuple = tuplesString[i];
            for (int j = 0; j < tuple.length(); j++) {
                tuples[i][j] = Character.getNumericValue(tuple.charAt(j));
            }
        }
        this.state = 1; // Start at State 1
        this.tapeIndex = 0; // Tape index starts at 0
        this.tape = tape.toCharArray(); // Initial tape size, adjust as needed
        this.console = consoleOutput;
        console.append("Card: "+tmModel+"\n");
    }

    public void run() {
        int numSteps =0;
    	tapeIndex = tape.length / 2; // Start in the middle of the tape
      
    	console.append("Initial tape position (head position is between the brackets):\n");
    	printTapeState();
    	console.append("Game Started\n");
    	
        while (state != 0 && tapeIndex >= 0 && tapeIndex < tape.length) {
            char currentSymbol = tape[tapeIndex];
            int currentTupleIndex = -1;

            // Find the tuple for the current state and input symbol
            for (int i = 0; i < tuples.length; i++) {
                if (tuples[i][0] == state && tuples[i][1] == Character.getNumericValue(currentSymbol)) {
                    currentTupleIndex = i;
                    break;
                }
            }

            if (currentTupleIndex != -1) {
                int nextState = tuples[currentTupleIndex][2];
                System.out.println(tuples[currentTupleIndex][3]);
                System.out.println(tuples[currentTupleIndex][3]);
                
                char writeSymbol = (tuples[currentTupleIndex][3]==0)?'0':'1';
                int direction = tuples[currentTupleIndex][4];

                state = nextState; // Update the state
                tape[tapeIndex] = writeSymbol; // Write symbol to tape
                console.append("Step= "+numSteps+", Tapepos="+tapeIndex+",  a[0]="+tuples[currentTupleIndex][0]+",  b[0]="+tuples[currentTupleIndex][1]+",  c[0]="+tuples[currentTupleIndex][2]+",  d[0]="+tuples[currentTupleIndex][3]+",  e[0]="+tuples[currentTupleIndex][4]+"\n");
                printTapeState();
               
                if (direction == 0) { 
                    tapeIndex--;
                } else { 
                    tapeIndex++;
                }
               

            } else {
                // No valid transition found, halt the machine
                break;
            }
            numSteps++;
        }

        if (state == 0) {
            console.append("Turing Machine halted at State 0\n");
            console.append("Final Tape Config is:\n");
            printTapeState();
        } else if (tapeIndex < 0 || tapeIndex >= tape.length) {
            console.append("Turing Machine halted due to tape bounds\n");
        }
    }

    private void printTapeState() {
        StringBuilder tapeState = new StringBuilder();
        for (int i = 0; i < tape.length; i++) {
            if (i == tapeIndex) {
                tapeState.append("[").append(tape[i]).append("]");
            } else {
                tapeState.append(tape[i]);
            }
            tapeState.append(" ");
        }
        console.append(tapeState.toString()+"\n");
    }

   
}
