using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 合同：子表-->t_contract_detail
    /// </summary>
    public class t_contract : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        ///<summary>
        /// 序号（录入）
        ///</summary>
        public int seq { get; set; }

        /// <summary>
        /// 合同编号
        /// </summary>
        [Required, MaxLength(50)]
        public string contractNo { get; set; }

        ///<summary>
        ///设备供应商(原 签订对象)
        ///</summary>
        [Required, MaxLength(50)]
        public string signTarget { get; set; }

        /// <summary>
        /// 合同日期
        /// </summary>
        public DateTime contractDate { get; set; }

        ///<summary>
        /// 合同附件
        ///</summary>
        [MaxLength(200)]
        public string filePath { get; set; }
    }
}
