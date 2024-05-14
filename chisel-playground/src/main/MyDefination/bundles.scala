package Mydefination

import chisel3._
import chisel3.util._
import Mydefination._
import Mydefination.const._

//IF和ram的接口模块

class RAM_IO extends Bundle {
    val en    = Output(Bool())
    val we    = Output(UInt(4.W))//指示对ram的写操作
    val addr  = Output(UInt(PC_WLEN.W))
    val wdata = Output(UInt(PC_WLEN.W))
    val rdata = Input(UInt(PC_WLEN.W))
}

//debug模块接口

class DEBUG extends Bundle {
    val wb_pc       = Output(UInt(PC_WLEN.W))
    val wb_rf_we    = Output(UInt(4.W))
    val wb_rf_wnum  = Output(UInt(PC_RLEN.W))
    val wb_rf_wdata = Output(UInt(PC_WLEN.W))
}

//
class BR_OUT extends Bundle {
    val taken  = Output(Bool())
    val target = Output(UInt(PC_WLEN.W))
}

//from IF to ID

class DS_BUS extends Bundle {
    val valid = Output(Bool())
    val pc    = Output(UInt(PC_WLEN.W))
    val inst  = Output(UInt(PC_WLEN.W))
}

class EX_BUS extends Bundle {
    val valid       = Output(Bool())
    val alu_op      = Output(UInt(12.W))
    val src1_data   = Output(UInt(PC_WLEN.W))
    val src2_data   = Output(UInt(PC_WLEN.W))
    val wb_src      = Output(UInt(WB_SEL_LEN.W))
    val rf_we       = Output(Bool())
    val mem_we      = Output(Bool())
    val dest        = Output(UInt(PC_RLEN.W))
    val rd_value    = Output(UInt(PC_WLEN.W))
    val pc          = Output(UInt(PC_WLEN.W))
}

class ME_BUS extends Bundle {
    val valid = Output(Bool())
    val wb_src = Output(UInt(WB_SEL_LEN.W))
    val rf_we = Output(Bool())
    val mem_we = Output(Bool())
    val dest = Output(UInt(PC_RLEN.W))
    val rd_value = Output(UInt(PC_WLEN.W))
    val alu_res = Output(UInt(PC_WLEN.W))
    val pc = Output(UInt(PC_WLEN.W))
}

class WB_BUS extends Bundle {
    val valid = Output(Bool())
    val wb_src = Output(UInt(WB_SEL_LEN.W))
    val rf_we = Output(Bool())
    val dest = Output(UInt(PC_RLEN.W))
    val alu_res = Output(UInt(PC_WLEN.W))
    val pc = Output(UInt(PC_WLEN.W))
}