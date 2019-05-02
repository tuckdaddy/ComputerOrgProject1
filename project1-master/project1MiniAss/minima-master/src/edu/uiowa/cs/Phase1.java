package edu.uiowa.cs;

import java.util.LinkedList;
import java.util.List;

public class Phase1 {
//test 6,8,9,10 failing
    /* Translates the MAL instruction to 1-3 TAL instructions
     * and returns the TAL instructions in a list
     *
     * mals: input program as a list of Instruction objects
     *
     * returns a list of TAL instructions (should be same size or longer than input list)
     */

    public static List<Instruction> tal = new LinkedList<>();

    public static List<Instruction> mal_to_tal(List<Instruction> mal) {
        int i;
        for (i = 0; i < mal.size(); i++) {
            Instruction currIn = mal.get(i);
            int rs = currIn.rs;
            int rd = currIn.rd;
            int rt = currIn.rt;
            int imm = currIn.immediate;
            //int jumpAddy = currIn.jump_address;
            //int shiftAmount = currIn.shift_amount;
            String label = currIn.label;
            String branchLabel = currIn.branch_label;

            //for when immediate is too high
            System.out.println(imm);
            int hiImm = imm >>>16;
            System.out.println(String.format("0x%08X", hiImm));
            int lowImm = imm & 0x0000FFFF;
            System.out.println(lowImm);
            //upper half use & operator

            //temp reg
            int t1=0, t2=0, t3=0, at = 0;
            //ADDIU or ORI
            if (((currIn.instruction_id.equals(Instruction.ID.addiu)||currIn.instruction_id.equals(Instruction.ID.ori)))) {
                at = 1;
                if(Math.abs(imm) > 65535) {
                    if ((currIn.instruction_id.equals(Instruction.ID.addiu))) {
                        tal.add(InstructionFactory.CreateLui(at, hiImm, label));
                        tal.add(InstructionFactory.CreateOri(at, at, lowImm));
                        tal.add(InstructionFactory.CreateAddu(rt, rs, at));
                        continue;
                    }
                    if (currIn.instruction_id.equals(Instruction.ID.ori)) {
                        tal.add(InstructionFactory.CreateLui(at, hiImm, label));
                        tal.add(InstructionFactory.CreateOri(at, at, lowImm));
                        tal.add(InstructionFactory.CreateOr(rt, rs, at));
                        continue;
                    }
                }
            }
            //BLT
            if((currIn.instruction_id.equals(Instruction.ID.blt))){
                if(rt>rs) at = 1;
                else at =0;

                tal.add(InstructionFactory.CreateSlt(at,rt,rs));
                tal.add(InstructionFactory.CreateBne(at,0,branchLabel));
                continue;
            }
            //BGE
            if((currIn.instruction_id.equals(Instruction.ID.bge))){
                tal.add(InstructionFactory.CreateSlt(at+1,rt,rs,label));
                rs =1;
                tal.add(InstructionFactory.CreateBeq(rs,rd,branchLabel));
                continue;
            }
            else tal.add(currIn);

        }
        System.out.println(tal);
        return tal;
    }
}
