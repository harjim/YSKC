using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 工艺线
    /// </summary>
    public class workshop : tablebase
    {
        // <summary>
        /// 工艺线Id
        /// </summary>
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司ID
        /// </summary>
        public int companyId { get; set; }

        /// <summary>
        /// 部门名称
        /// </summary>
        [MaxLength(100)]
        public string workshopName { get; set; }
        /// <summary>
        /// 父id
        /// </summary>
        public int parentId { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(300)]
        public string remark { get; set; }
        /// <summary>
        /// 全路径
        /// </summary>
        [MaxLength(100)]
        public string fullPath { get; set; }
        /// <summary>
        /// 序列
        /// </summary>
        public int seq { get; set; }
    }
}
