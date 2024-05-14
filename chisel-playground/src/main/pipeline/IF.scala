package mycpu

import chisel3._
import chisel3.util._
import Mydefination._
import Mydefination.const._

class IF_Stage extends Module {
    val io = IO(new Bundle {
        val inst = new RAM_IO()
        val br = Flipped(new BR_OUT())
        val to_ds = new DS_BUS()
        val ds_allowin = Input(Bool())
    })

    val to_fs_valid = Wire(Bool())
    val fs_ready_go = Wire(Bool())
    val fs_valid = RegInit(false.B)
    val fs_allowin = Wire(Bool())

    to_fs_valid := RegNext(!reset.asBool) & (!reset.asBool)
    fs_ready_go := true.B
    fs_allowin  := (!fs_valid) | (fs_ready_go & io.ds_allowin)//前面完成启动功能，后面确认后路通，自己通
    when (fs_allowin) {
        fs_valid := to_fs_valid
    }
    io.to_ds.valid := fs_valid & fs_ready_go//启动信号的传递

    val pc     = RegInit("h1bfffffc".asUInt(32.W))
    val seq_pc = Wire(UInt(32.W))
    val nxt_pc = Wire(UInt(32.W))

    //PC跳转控制    
    seq_pc := pc + 4.U
    nxt_pc := Mux(io.br.taken, io.br.target, seq_pc)

    //总线通信
    io.inst.we    := 0.U(4.W)
    io.inst.en    := to_fs_valid & fs_allowin
    io.inst.addr  := nxt_pc
    io.inst.wdata := 0.U(PC_WLEN.W)

    //将取来的指令和对应的PC号传入ID
    io.to_ds.pc    := pc
    io.to_ds.inst  := io.inst.rdata

    when (to_fs_valid && fs_allowin) {
        pc := nxt_pc
    }
}