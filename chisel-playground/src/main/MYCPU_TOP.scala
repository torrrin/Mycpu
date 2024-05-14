package mycpu

import chisel3._
import chisel3.util._
import Mydefination._

class MYCPU_TOP extends Module {
    val io = IO(new Bundle {
        val inst = new RAM_IO()
        val data = new RAM_IO()
        val debug = new DEBUG()
    })

    val IF = Module(new IF_Stage())
    val ID = Module(new ID_Stage())
    val EX = Module(new EX_Stage())
    val MEM = Module(new MEM_Stage())
    val WB = Module(new WB_Stage())
    val regfile = Module(new RegFile())

    IF.io.inst <> io.inst
    IF.io.to_ds <> ID.io.to_ds
    IF.io.br <> ID.io.br
    IF.io.ds_allowin <> ID.io.ds_allowin

    ID.io.es_allowin <> EX.io.es_allowin
    ID.io.to_es <> EX.io.to_es
    ID.io.rj <> regfile.io.raddr1
    ID.io.rk <> regfile.io.raddr2
    ID.io.rd <> regfile.io.raddr3
    ID.io.reg_rdata1 <> regfile.io.rdata1
    ID.io.reg_rdata2 <> regfile.io.rdata2
    ID.io.reg_rdata3 <> regfile.io.rdata3

    EX.io.ms_allowin <> MEM.io.ms_allowin
    EX.io.to_ms     <> MEM.io.to_ms

    MEM.io.ws_allowin <> WB.io.ws_allowin
    MEM.io.to_ws      <> WB.io.to_ws
    MEM.io.data       <> io.data

    WB.io.debug      <> io.debug
    WB.io.data_rdata <> io.data.rdata
    WB.io.rf_we      <> regfile.io.we
    WB.io.rf_waddr   <> regfile.io.waddr
    WB.io.rf_wdata   <> regfile.io.wdata
}