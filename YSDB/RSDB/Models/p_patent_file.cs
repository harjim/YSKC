using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_patent_file : tablebase
    {
        [Key]
        public int id { get; set; }

        public int? patentPlanId { get; set; }
        /// <summary>
        /// 文件路径
        /// </summary>
        [Required, MaxLength(200)]
        public string filepath { get; set; }
        ///<summary>
        /// 1：受理通知书 2：实质审查资料 3：授权通知书 4：知识产权证书 5：缴费收据 6：其他
        ///<summary>
        public int fileType { get; set; }
        /// <summary>
        /// 文件名称
        /// </summary>
        [Required, MaxLength(100)]
        public string fileName { get; set; }
        /// <summary>
        /// 专利号
        /// </summary>
        [MaxLength(50)]
        public string patentNo { get; set; }
    }
}