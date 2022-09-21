using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 删除数据备份
    /// </summary>
    public class c_backup_data
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 操作数据的表
        /// </summary>
        [Required, MaxLength(30)]
        public String tableName { get; set; }
        /// <summary>
        /// 数据
        /// </summary>
        [Required, Column(TypeName = "text")]
        public string data { get; set; }

        public int creatorId { get; set; }
        public DateTime createTime { get; set; }
        public int msCreatorId { get; set; }
        /// <summary>
        /// 操作类型：0删除，1编辑【编辑会有多条记录】
        /// </summary>
        public int type { get; set; }


    }
}
