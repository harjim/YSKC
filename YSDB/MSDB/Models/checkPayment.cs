using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MSDB.Models
{
    public class checkPayment : tablebase
    {
        [Key]
        public int id { get; set; }

        public int customerId { get; set; }

        public int year { get; set; }

        ///<summary>
        /// 业务员id
        ///</summary>
        public int ownerId { get; set; }

        ///<summary>
        /// 部门id
        ///</summary>
        public int deptId { get; set; }

        ///<summary>
        /// 技术人员
        ///</summary>
        public int techId { get; set; }
        
        ///<summary>
        /// 分公司总经理
        ///</summary>
        public int ownerMangerId { get; set; }

        ///<summary>
        /// 财务经理
        ///</summary>
        public int finaManagerId { get; set; }

        ///<summary>
        /// 财务总监
        ///</summary>
        public int finaDirectorId { get; set; }

        ///<summary>
        /// 查新日期
        ///</summary>
        public DateTime checkDate { get; set; }

        ///<summary>
        /// 查新机构
        ///</summary>
        public int checkInstId { get; set; }

        ///<summary>
        ///查新个数(项目个数)
        ///</summary>
        public int rdCnt { get; set; }

        ///<summary>
        /// 查新单价
        ///</summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal unitPrice { get; set; }

        ///<summary>
        /// 总金额(总价)
        ///</summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal totalAmount { get; set; }

        ///<summary>
        /// 查新账号
        ///</summary>
        [MaxLength(50)]
        public string checkUsername { get; set; }

        ///<summary>
        /// 查新密码
        ///</summary>
        [MaxLength(30)]
        public string checkPassword { get; set; }

        ///<summary>
        /// 付款方式(字典表：type=18)
        ///</summary>
        [MaxLength(10)]
        public string payType { get; set; }
        
        ///<summary>
        /// 付款凭证(多文件)
        ///</summary>
        [MaxLength(500)]
        public string paymentFile { get; set; }

        ///<summary>
        /// 备注
        ///</summary>
        [MaxLength(200)]
        public string remark { get; set; }
    }
}
