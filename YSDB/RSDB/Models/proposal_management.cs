using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 提案附件
    /// </summary>
    public class proposal_management : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }
        ///<summary>
        /// RD
        ///</summary>
        public int? projectId { get; set; }

        /// <summary>
        /// 年份
        /// </summary>
        public int year { get; set; }

        /// <summary>
        /// 标题
        /// </summary>
        [Required, MaxLength(100)]
        public string title { get; set; }
        /// <summary>
        /// 附件
        /// </summary>
        [Required, MaxLength(1000)]
        public string filePath { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(500)]
        public string remark { get; set; }

        /// <summary>
        /// 0立项素材、1现场图片、2立项对接表、3体系文件提案、4专利提案、5费用规划、6其他提案
        /// </summary>
        public int? type { get; set; }

    }
}
