using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目现场考察时需要相关信息、数据
    /// </summary>
    public class t_projectOther : tablebase
    {
        [Key]
        public int id { get; set; }

        /// <summary>
        /// 项目id，t_project
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 区代码
        /// </summary>
        [Required, MaxLength(20)]
        public string areaCode { get; set; }
        /// <summary>
        /// 单位简介及建设依据
        /// </summary>
        [Required, MaxLength(300)]
        public string synopsis { get; set; }
        /// <summary>
        /// 主要建设内容和目标
        /// </summary>
        [Required, MaxLength(300)]
        public string targetAndContent { get; set; }
        /// <summary>
        /// 地址
        /// </summary>
        [Required, MaxLength(300)]
        public string address { get; set; }
    }
}
