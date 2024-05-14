package mycpu

import chisel3._
import chisel3.util._
import Mydefination._
import Mydefination.const._

class RegFile extends Module {
    val io = IO(new Bundle {
        val raddr1 = Input(UInt(PC_RLEN.W))
        val rdata1 = Output(UInt(PC_WLEN.W))
        val raddr2 = Input(UInt(PC_RLEN.W))
        val rdata2 = Output(UInt(PC_WLEN.W))
        val raddr3 = Input(UInt(PC_RLEN.W))
        val rdata3 = Output(UInt(PC_WLEN.W))
        val we     = Input(Bool())
        val waddr  = Input(UInt(PC_RLEN.W))
        val wdata  = Input(UInt(PC_WLEN.W))
    })

  // 定义一个32x32位的寄存器文件
  val rf = Mem(32, UInt(PC_WLEN.W))

  // 写操作
  when(io.we) {
    rf.write(io.waddr, io.wdata)
  }

  // 读端口1
  // 如果读地址是0，则直接输出0，否则从寄存器文件读取数据
  io.rdata1 := Mux(io.raddr1 === 0.U, 0.U(PC_WLEN.W), rf.read(io.raddr1))

  // 读端口2
  // 如果读地址是0，则直接输出0，否则从寄存器文件读取数据
  io.rdata2 := Mux(io.raddr2 === 0.U, 0.U(PC_WLEN.W), rf.read(io.raddr2))
  io.rdata3 := Mux(io.raddr3 === 0.U, 0.U(PC_WLEN.W), rf.read(io.raddr3))
}