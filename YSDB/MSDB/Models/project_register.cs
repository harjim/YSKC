using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class project_register : tablebase
    {

        [Key]
        public int id { get; set; }

        public int projectId { get; set; }

        ///<summary>
        /// 备案名称
        /// </summary>
        [Required, MaxLength(100)]
        public string registerName { get; set; }

        public DateTime beginDate { get; set; }

        public DateTime endDate { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal amount { get; set; }

        ///<summary>
        /// 变更情况
        /// </summary>
        [MaxLength(200)]
        public string changeContent { get; set; }


        ///<summary>
        /// 投资金额
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal investAmount { get; set; }

        public DateTime? finishDate { get; set; }

        /// <summary>
        /// 备案原件所在地
        ///</summary>
        [MaxLength(200)]
        public string originalPlace { get; set; }
        /// <summary>
        /// 备案证扫描件
        ///</summary>
        [MaxLength(200)]
        public string filePath { get; set; }
         /// <summary>
        /// 备注
        ///</summary>
        [MaxLength(200)]
        public string remark { get; set; }
        /// <summary>
        /// 备案文件
        ///</summary>
        [MaxLength(200)]
        public string registerFile { get; set; }

        ///<summary>
        /// 备案证号
        /// </summary>
        [Required, MaxLength(100)]
        public string registerNo { get; set; }
    }
}