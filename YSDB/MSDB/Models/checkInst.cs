using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MSDB.Models
{
    ///<summary>
    /// 查新机构
    ///</summary>
    public class checkInst : tablebase
    {
        [Key]
        public int id { get; set; }

        ///<summary>
        /// 查询机构名称
        ///</summary>
        [Required, MaxLength(50)]
        public string instName { get; set; }

        ///<summary>
        /// 账户名
        ///</summary>
        [Required, MaxLength(50)]
        public string accountName { get; set; }

        ///<summary>
        /// 开户行（开户银行）
        ///</summary>
        [Required, MaxLength(50)]
        public string accountBank { get; set; }

        ///<summary>
        /// 账号
        ///</summary>
        [Required, MaxLength(50)]
        public string accountNo { get; set; }

        ///<summary>
        /// 快递费（邮费）
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal postage { get; set; }

        ///<summary>
        /// 付款备注
        ///</summary>
        [MaxLength(500)]
        public string payRemark { get; set; }

        ///<summary>
        /// 备注
        ///</summary>
        [MaxLength(200)]
        public string remark { get; set; }

        ///<summary>
        /// 联系人
        ///</summary>
        [MaxLength(20)]
        public string linkMan { get; set; }

        ///<summary>
        /// 联系电话
        ///</summary>
        [MaxLength(20)]
        public string linkTel { get; set; }

        ///<summary>
        /// 附件
        ///</summary>
        [MaxLength(200)]
        public string filePath { get; set; }
    }
}
