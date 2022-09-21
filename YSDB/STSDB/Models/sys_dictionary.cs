using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace STSDB.Models
{
    public class sys_dictionary
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 字典类型
        /// </summary>
        public byte type { get; set; }
        /// <summary>
        /// 字典key
        /// </summary>
        [MaxLength(50)]
        public string key { get; set; }
        /// <summary>
        /// 字典值
        /// </summary>
        [MaxLength(300)]
        public string value { get; set; }
        /// <summary>
        /// 父节点
        /// </summary>
        [MaxLength(50)]
        public string parentKey { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(300)]
        public string remark { get; set; }
        /// <summary>
        /// 排序
        /// </summary>
        public int order { get; set; }
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }

    }
}