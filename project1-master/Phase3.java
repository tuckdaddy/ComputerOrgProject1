package edu.uiowa.cs;

import sun.rmi.server.InactiveGroupException;

import java.util.LinkedList;
import java.util.List;

public class Phase3 {

    /* Translate each Instruction object into
     * a 32-bit number.
     *
     * tals: list of Instructions to translate
     *
     * returns a list of instructions in their 32-bit binary representation
     *
     */

    public static List<Integer> bitbin32 = new LinkedList<>();

    public static List<Integer> translate_instructions(List<Instruction> tals) {
        //R-Type: op(6),rs(5),rt(5),rd(5),Shamt(5),funct(6)
        //add, sub. and, xor, mul, com, jr
        //I-Type: op(6),rs(5),rt(5), imm(16)
        //adddi,lw,sw,beg
        for (Instruction current : tals) {
            switch (current.instruction_id) {
                //R-type

                case addu: case slt: case or:
                    int funct = 0;
                    if(current.instruction_id.equals(Instruction.ID.addu)) {
                        funct = 33;
                    }
                    if(current.instruction_id.equals(Instruction.ID.or)){
                        funct = 37;
                    }
                    if(current.instruction_id.equals(Instruction.ID.slt)){
                        funct = 42;
                    }
                    int shamt = 0<<10;
                    int b10 = funct^shamt;
                    int rd = current.rd<<11;
                    int b15 = rd^b10;
                    int rt=current.rt<<16;
                    int b20 = b15^rt;
                    int rs = current.rs<<21;
                    int b25 = b20^rs;
                    String ans = Integer.toHexString(b25);
                    bitbin32.add(Integer.parseInt(ans,16));
                    System.out.println(bitbin32);
                    break;//I-type
                case addiu: case beq: case bne: case ori: case lui:
                    int rsi = current.rs<<21;
                    int rti = current.rt<<16;
                    int imm= current.immediate;// <<00;
                    if(current.instruction_id.addiu == current.instruction_id) {
                        int opcodeI = 33<<26;
                        b20 = imm^rti;
                        b25 = b20^rsi;
                        int b32 = opcodeI^b25;
                        ans = Integer.toHexString(b25);
                        bitbin32.add(Integer.parseInt(ans,16));
                        continue;
                    }
                    if(current.instruction_id.beq == current.instruction_id) {
                        //int opcodeI = 4<< 26;
                       //  System.out.println(opcodeI);
                        //imm = current.immediate;
                        
                        System.out.println((imm));
                        b20 = imm^rti;
                        b25 = b20^rsi;
                        int opcodeI = 0100 << 26;
                        int b32 = opcodeI^b25;
                        //int jaddy = current.jump_address &0x0000FFFF;
                        ans = Integer.toHexString(b32);
                        bitbin32.add(Integer.parseInt(ans,2));
                        continue;
                    }
                    if(current.instruction_id.bne==current.instruction_id){
                        int opcodeI = 5 << 26;
                        imm = imm ^0xFFFF0000;
                        b20 = imm^rti;
                        b25 = b20^rsi;
                        int b32 = opcodeI^b25;
                        ans = Integer.toHexString(b32);
                        bitbin32.add(Integer.parseInt(ans,16));
                        continue;
                    }
                    if (current.instruction_id.ori==current.instruction_id){
                        int opcodeI = 13<<26;
                        //imm= 00;
                        //imm = imm ^0X;// ORI ,BEQ need fixed
                        b20 = imm^rti;
                        b25 = b20^rsi;
                        int b32 = opcodeI^b25;
                        ans = Integer.toHexString(b32);
                        bitbin32.add(Integer.parseInt(ans,16));
                        continue;
                    }
                    if (current.instruction_id.lui==current.instruction_id){
                        int opcodeI = 15 <<26;
                        b20 = imm^rti;
                        b25 = b20^rsi;
                        int b32 =opcodeI^b25;
                        ans = Integer.toHexString(b32);
                        bitbin32.add(Integer.parseInt(ans,16));
                        continue;
                    }
                    System.out.println(bitbin32);
                    break;


            }
        }
        return bitbin32;
    }
}