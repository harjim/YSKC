using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class h3_agreement
    {
        //对象Id
        [Key,MaxLength(50)]
        public string objectId{ get; set; }
        
        // 客户
        [MaxLength(80)]
        public string Client { get; set; }
        // 客户名称
        [MaxLength(100)]
        public string clientname { get; set; }
        // 合同编号
        [MaxLength(50)]
        public string SeqNo { get; set; }

        //业务员
        [MaxLength(30)]
        public string OwnerId { get; set; }
        //业务员所属部门
        [MaxLength(100)]
        public string OwnerDeptId { get; set; }
        // 分公司总经理
        [MaxLength(30)]
        public string Approvers { get; set; }
        //合同状态
        [MaxLength(20)]
        public string ConStatus { get; set; }
        // 谈单经理
        [MaxLength(30)]
        public string F0000022 { get; set; } 
        // 区域技术总监
        [MaxLength(30)]
        public string F0000021 { get; set; }
        // 区域技术经理
        [MaxLength(30)]
        public string F0000016 { get; set; }
        // 技术负责人
        [MaxLength(30)]
        public string F0000018 { get; set; }

        // 区域财务经理
        [MaxLength(30)]
        public string F0000017 { get; set; }
        // 财务负责人
        [MaxLength(30)]
        public string F0000019 { get; set; }

        public DateTime CreatedTime { get; set; }
        public DateTime ModifiedTime { get; set; }
    }
}