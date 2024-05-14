package Mydefination

import chisel3._
import chisel3.util._



object const{
    //全局
    val PC_WLEN = 32 //机器字长
    val PC_RLEN  = 5 

    //指令的运算器编号

    val OP_LEN = 4
    val ALU_X = 15.U(OP_LEN.W)
    val ALU_ADD = 0.U(OP_LEN.W)
    val ALU_LD = 0.U(OP_LEN.W)
    val ALU_ST = 0.U(OP_LEN.W)
    val ALU_SUB = 1.U(OP_LEN.W)
    val ALU_SLT = 2.U(OP_LEN.W)
    val ALU_SLTU = 3.U(OP_LEN.W)
    val ALU_AND = 4.U(OP_LEN.W)
    val ALU_NOR = 5.U(OP_LEN.W)
    val ALU_OR = 6.U(OP_LEN.W)
    val ALU_XOR = 7.U(OP_LEN.W)
    val ALU_SLL = 8.U(OP_LEN.W)
    val ALU_SRL = 9.U(OP_LEN.W)
    val ALU_SRA = 10.U(OP_LEN.W)
    val ALU_LU12I = 11.U(OP_LEN.W)



    val BR_LEN = 3
    val BR_X = 0.U(BR_LEN.W)
    val BR_S = 1.U(BR_LEN.W)
    val BR_BNE = 2.U(BR_LEN.W)
    val BR_BEQ = 3.U(BR_LEN.W)
    val BR_BL = 4.U(BR_LEN.W)

    val MEM_SEL_LEN = 4
    val MEM_X = 0.U(MEM_SEL_LEN.W)
    val MEM_S = 15.U(MEM_SEL_LEN.W)

    val RF_SEL_LEN = 1
    val RF_X = 0.U(RF_SEL_LEN.W)
    val RF_S = 1.U(RF_SEL_LEN.W)

    val WB_SEL_LEN = 2
    val WB_X = 0.U(WB_SEL_LEN.W)
    val WB_ALU = 1.U(WB_SEL_LEN.W)
    val WB_PC = 2.U(WB_SEL_LEN.W)
    val WB_MEM = 3.U(WB_SEL_LEN.W)
}