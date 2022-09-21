using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class project_timeline
    {
        [Key]
        public int id { get; set; }
        ///<summary>
        /// 类型
        /// </summary>
        public int itemType { get; set; }

        public int projectId { get; set; }
        ///<summary>
        /// 开始时间
        /// </summary>
        public DateTime beginTime { get; set; }
        ///<summary>
        /// 结束时间
        /// </summary>
        public DateTime endTime { get; set; }
        ///<summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }
        ///<summary>
        /// 更新时间
        /// </summary>
        public DateTime updateTime { get; set; }
        ///<summary>
        ///结果
        ///</summary>
        [MaxLength(1000)]
        public string result { get; set; }

    }
}