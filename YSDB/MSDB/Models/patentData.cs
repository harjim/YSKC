using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 专利表
    /// </summary>
    public class patentData
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 专利号
        /// </summary>
        [Required, MaxLength(50)]
        public string patentNo { get; set; }
        /// <summary>
        /// 发明名称
        /// </summary>
        [MaxLength(200)]
        public string patentName { get; set; }
        /// <summary>
        /// 发明人
        /// </summary>
        [MaxLength(200)]
        public string inventor { get; set; }
        /// <summary>
        /// 申请人
        /// </summary>
        [MaxLength(300)]
        public string applyName { get; set; }
        /// <summary>
        /// 申请日期
        /// </summary>
        public DateTime? applyDateTime { get; set; }

        ///<summary>
        /// 类型
        ///</summary>
        public int? mainType { get; set; }
        /// <summary>
        /// 主分类号 
        /// </summary>
        [MaxLength(100)]
        public string mainTypeNo { get; set; }
        /// <summary>
        /// 案件状态
        /// </summary>
        [MaxLength(50)]
        public string caseStatus { get; set; }

        [MaxLength(200)]
        public string address { get; set; }

        /// <summary>
        /// 公告号
        /// </summary>
        [MaxLength(20)]
        public string publicNo { get; set; }
        /// <summary>
        /// 公告日
        /// </summary>
        public DateTime? publicDate { get; set; }

        public DateTime createTime { get; set; }

        public DateTime lastUpdateTime { get; set; }

        [MaxLength(20), Required]
        public string queryWord { get; set; }

        /// <summary>
        /// 法律状态请求段
        /// </summary>
        [MaxLength(100)]
        public string anx { get; set; }
    }
}
