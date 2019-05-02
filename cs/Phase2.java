package edu.uiowa.cs;

import java.util.LinkedList;
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
    public static LinkedList<Instruction> addyResolvedTals = new LinkedList<Instruction>();
    public static LinkedList<LinkedList<Object>> mappingsList = new LinkedList<LinkedList<Object>>();

    public static List<Instruction> resolve_addresses(List<Instruction> unresolved, int first_pc) {
        int pc = first_pc;
        int jaddy = 0;
        for(int i = 0;i<unresolved.size();i++){
            Instruction currIn = unresolved.get(i);
            LinkedList<Object> mappin = new LinkedList<Object>();

            if(!currIn.label.isEmpty()){
                // save the address of line
                String label = currIn.label;
                int label_pc = first_pc;
                mappin.add(label);
                mappin.add(label_pc);
            }
            first_pc+=4;
            if(mappin.size() !=0){
                mappingsList.add(mappin);
            }

        }
        for(int i = 0; i< unresolved.size(); i++){
            Instruction current = unresolved.get(i);
            if(current.instruction_id.equals(Instruction.ID.j)){
                jaddy = current.jump_address +(pc/4)-2 /*&0x0000FFFF*/;
                System.out.println(current.jump_address);
                System.out.println(jaddy);
                for(int k =0; k<mappingsList.size(); k++){
                    LinkedList<Object> currMappin = mappingsList.get(k);
                    int addy = (Integer) currMappin.get(1);
                    if(currMappin.get(0) == current.branch_label){
                        Instruction newIn = new Instruction(current.instruction_id,
                                current.rd,
                                current.rs,
                                current.rt,
                                current.immediate,
                                jaddy,
                                current.shift_amount,
                                current.label,
                                "");
                        current = newIn;
                    }
                }
            }
            if(current.instruction_id.equals(Instruction.ID.beq)
                    ||current.instruction_id.equals(Instruction.ID.blt)
                    ||current.instruction_id.equals(Instruction.ID.bge)
                    ||current.instruction_id.equals(Instruction.ID.bne)){
                for(int k =0; k<mappingsList.size(); k++){
                LinkedList<Object> currMappin = mappingsList.get(k);
                int addy = (Integer) currMappin.get(1);
                if(currMappin.get(0) == current.branch_label){
                    int immAddy = (addy-pc)/4 -1;
                    Instruction newIn = new Instruction(current.instruction_id,
                            current.rd,
                            current.rs,
                            current.rt,
                            immAddy,
                            current.jump_address,
                            current.shift_amount,
                            current.label,
                            "");
                    current = newIn;
                }
            }
        }
            pc+=4;
            addyResolvedTals.add(current);
        }
        return addyResolvedTals;
    }

}
