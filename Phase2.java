package edu.uiowa.cs;

import java.util.List;

public class Phase2 {

    /* Returns a list of copies of the Instructions with the
     * immediate field (i-type) or jump_address (j-type) of the instruction filled in
     * with the address calculated from the branch_label.
     *
     * The instruction should not be changed if it is not a branch or jump instruction.
     *
     * unresolved: input program, whose branch/jump instructions don't have resolved immediate/jump_address
     * first_pc: address where the first instruction of the program will eventually be placed in memory
     */
    public static List<Instruction> resolve_addresses(List<Instruction> unresolved, int first_pc) {
        return null;
    }

}
