using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class dept : tablebase
    {
        /// <summary>
        /// 部门Id
        /// </summary>
        [Key]
        public short id { get; set; }

        public int companyId { get; set; }
        /// <summary>
        /// 部门名称
        /// </summary>
        [Required, MaxLength(100)]
        public string deptName { get; set; }
        /// <summary>
        /// 父id
        /// </summary>
        public short parentId { get; set; }

        /// <summary>
        /// 部门级别
        /// </summary>
        public int level { get; set; }

        /// <summary>
        /// 部门标识, 通过id自动生成
        /// </summary>
        [Required, MaxLength(80)]
        public string identity { get; set; }

        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(300)]
        public string remark { get; set; }
        /// <summary>
        /// 全路径[id]
        /// </summary>
        [MaxLength(300)]
        public string fullPath { get; set; }
        /// <summary>
        /// 全路径[名称]
        /// </summary>
        [MaxLength(300)]
        public string fullname { get; set; }
    }
}
