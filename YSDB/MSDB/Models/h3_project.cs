using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    // yrb9f3we4x4wfzqsa60ylv0h6
    public class h3_project
    {
        //对象Id
        [Key,MaxLength(50)]
        public string objectId{ get; set; }
        
        //客户名称
        [MaxLength(100)]
        public string F0000006 { get; set; }

        // 项目种类
        [MaxLength(100)]
        public string F0000534 { get; set; }
        // 年份
        public int ProjectYear { get; set; }

        //业务员
        [MaxLength(30)]
        public string Sales { get; set; }
        //业务员所属部门
        [MaxLength(100)]
        public string OwnerDeptId { get; set; }
        // 分公司总经理
        [MaxLength(30)]
        public string F0000523 { get; set; }
        // 谈单经理
        [MaxLength(30)]
        public string F0000524 { get; set; } 
        // 区域技术经理
        [MaxLength(30)]
        public string F0000111 { get; set; }
        // 技术负责人
        [MaxLength(30)]
        public string F0000112 { get; set; }

        // 区域财务经理
        [MaxLength(30)]
        public string F0000114 { get; set; }
        // 财务负责人
        [MaxLength(30)]
        public string F0000115 { get; set; }

        // 预计销售额
        [MaxLength(20)]
        public string F0000537 { get; set; }
        // 预计所得税
        [MaxLength(20)]
        public string F0000538 { get; set; }
        // 预计归集研发费
        [MaxLength(20)]
        public string F0000539 { get; set; }
        // 预计减免所得税
        [MaxLength(20)]
        public string F0000540 { get; set; }
        // 预计投资金额
        [MaxLength(20)]
        public string F0000541 { get; set; }
        // 预计补助金额
        [MaxLength(20)]
        public string F0000542 { get; set; }

        public DateTime? CreatedTime { get; set; }
        public DateTime? ModifiedTime { get; set; }
    }
}