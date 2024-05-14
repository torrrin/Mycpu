package mycpu

import chisel3._
import chisel3.util._
import Mydefination._
import Mydefination.const._

class ALU extends Module {
  val io = IO(new Bundle {
    val aluOp   = Input(UInt(OP_LEN.W))
    val aluSrc1 = Input(UInt(PC_WLEN.W))
    val aluSrc2 = Input(UInt(PC_WLEN.W))
    val aluResult = Output(UInt(PC_WLEN.W))
  })

  // Control code decomposition
  val opAdd  = (io.aluOp === ALU_ADD)
  val opSub  = (io.aluOp === ALU_SUB)
  val opSlt  = (io.aluOp === ALU_SLT)
  val opSltu = (io.aluOp === ALU_SLTU)
  val opAnd  = (io.aluOp === ALU_AND)
  val opNor  = (io.aluOp === ALU_NOR)
  val opOr   = (io.aluOp === ALU_OR)
  val opXor  = (io.aluOp === ALU_XOR)
  val opSll  = (io.aluOp === ALU_SLL)
  val opSrl  = (io.aluOp === ALU_SRL)
  val opSra  = (io.aluOp === ALU_SRA)
  val opLui  = (io.aluOp === ALU_LU12I)

  // Adder/Subtractor logic
  val addSubResult = Wire(UInt(PC_WLEN.W))
  val adderResult = Mux(opSub || opSlt || opSltu, io.aluSrc1 - io.aluSrc2, io.aluSrc1 + io.aluSrc2)
  addSubResult := adderResult

  // Set less than (signed)
  val sltResult = Wire(UInt(PC_WLEN.W))
  sltResult := 0.U
  when(io.aluSrc1.asSInt < io.aluSrc2.asSInt) {
    sltResult := 1.U
  }

  // Set less than (unsigned)
  val sltuResult = Wire(UInt(PC_WLEN.W))
  sltuResult := 0.U
  when(io.aluSrc1.asUInt < io.aluSrc2.asUInt) {  // Assuming adderResult(31) is the carry out
    sltuResult := 1.U
  }

  // Bitwise operations
  val andResult = io.aluSrc1 & io.aluSrc2
  val norResult = ~(io.aluSrc1 | io.aluSrc2)
  val orResult = io.aluSrc1 | io.aluSrc2
  val xorResult = io.aluSrc1 ^ io.aluSrc2

  // Shift operations
  val sllResult = io.aluSrc1 << io.aluSrc2(4,0)
  val srlResult = io.aluSrc1 >> io.aluSrc2(4,0)
  val sraResult = (io.aluSrc1.asSInt >> io.aluSrc2(4,0)).asUInt

  // Load Upper Immediate
  val luiResult = io.aluSrc2

  // ALU result selection
  io.aluResult := MuxCase(0.U, Seq(
    opAdd.orR  -> addSubResult,
    opSub.orR  -> addSubResult,
    opSlt.orR  -> sltResult,
    opSltu.orR -> sltuResult,
    opAnd.orR  -> andResult,
    opNor.orR  -> norResult,
    opOr.orR   -> orResult,
    opXor.orR  -> xorResult,
    opSll.orR  -> sllResult,
    opSrl.orR  -> srlResult,
    opSra.orR  -> sraResult,
    opLui.orR  -> luiResult
  ))
}