package edu.uiowa.cs;

import javax.swing.*;
import java.util.ArrayList;
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

    public static List<Integer> bitbin32 = new ArrayList<>();

    public static List<Integer> translate_instructions(List<Instruction> tals) {
        //R-Type: op(6),rs(5),rt(5),rd(5),Shamt(5),funct(6)
        //add, sub. and, xor, mul, com, jr
        //I-Type: op(6),rs(5),rt(5), imm(16)
        //adddi,lw,sw,beg
        for (Instruction current : tals) {
            switch (current.instruction_id) {

                //R-type
                case addu: case slt: case or:
                    System.out.println("this is r-type"+ current.instruction_id);
                    String funct = "0";
                    if(current.instruction_id.equals(Instruction.ID.addu)){
                        funct = Integer.toBinaryString(33);
                    }
                    if(current.instruction_id.equals(Instruction.ID.or)){
                        funct = Integer.toBinaryString(37);

                    }
                    if(current.instruction_id.equals(Instruction.ID.slt)){
                        funct = Integer.toBinaryString(42);
                    }
                    while (funct.length() < 6){
                        funct = "0" + funct;
                    }
                    String shamt = "00000";
                    //String bit10 = Integer.toBinaryString(shamt ^funct);
                    String  rd = Integer.toBinaryString(current.rd);
                    while (rd.length() < 5){ rd ="0" + rd; }
                    //int b15 = rd^b10;       //what is this doing
                    String rt = Integer.toBinaryString(current.rt);
                    while (rt.length() < 5){ rt ="0" + rt; }
                   //int b20 = b15 ^ rt;
                    String rs = Integer.toBinaryString(current.rs);
                    while (rs.length() < 5){ rs ="0" + rs; }
                   // int b25 = b20 ^rs;
                    String opcode = "000000";
                    String ans =  opcode + rs + rt + rd + shamt + funct;
                    /*if(ans !=null && !"".equals(ans)){
                        try{
                            Integer intCost = Integer.parseInt(ans,16);
                        }catch (NumberFormatException e){
                            System.out.println("This is not a number");
                            System.out.println(e.getMessage());
                        }
                    }
                    */
                    bitbin32.add(Integer.parseInt(ans,2));
                    System.out.println("shamt   " +shamt);
                    System.out.println("funct   " +funct);
                    System.out.println("rd      " +rd);
                    System.out.println("rt      " + rt);
                    System.out.println("rs      " + rs);
                    System.out.println("opcode  " + opcode);
                    System.out.println(bitbin32);

                    break;
                    //I-type
                case addiu: case beq: case bne: case ori: case lui:
                    System.out.println("this is i-type"+ current.instruction_id);
                    String opCode = "0";
                    if (current.instruction_id.equals(Instruction.ID.beq)){
                         opCode = Integer.toBinaryString(4);
                    }
                    if (current.instruction_id.equals(Instruction.ID.bne)){
                         opCode = Integer.toBinaryString(5);
                    }
                    if (current.instruction_id.equals(Instruction.ID.addiu)){
                         opCode = Integer.toBinaryString(9);
                    }
                    if (current.instruction_id.equals(Instruction.ID.ori)){
                         opCode = Integer.toBinaryString(13);

                    }
                    if (current.instruction_id.equals(Instruction.ID.lui)){
                         opCode = Integer.toBinaryString(15);
                    }
                    while(opCode.length() < 6){ opCode = "0" + opCode;}
                    int bimm = current.immediate ^ 0xFFFF0000;

                    System.out.println("this is bimm    " +Integer.toBinaryString(bimm));
                    String fimm = Integer.toBinaryString(bimm ^ 0xFFFF0000);
                    System.out.println(fimm);
                    while (fimm.length() <16){fimm = "0" + fimm; }
                    String brt = Integer.toBinaryString(current.rt);
                    while(brt.length() < 5){brt = "0" + brt; }
                    String brs = Integer.toBinaryString(current.rs);
                    while(brs.length() < 5){ brs = "0" + brs; }
                    String a = "";
                    if(current.immediate < 0){
                        a = opCode + brs + brt + Integer.toBinaryString(bimm);
                    }
                    else {
                        a = opCode + brs + brt + fimm;

                    }
                    System.out.println("opCode  " +opCode);
                    System.out.println("brs     " + brs);
                    System.out.println("brt     " + brt);
                    System.out.println("This is curretn immediate" +current.immediate);
                    System.out.println("fimm    " + fimm);
                    bitbin32.add(Integer.parseInt(a,2));
                    System.out.println(bitbin32);
                    break;

            }
        }
        return bitbin32;
    }
}