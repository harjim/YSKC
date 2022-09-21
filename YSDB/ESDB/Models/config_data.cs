using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace ESDB.Models
{
    public class config_data
    {
        [Key]
        public int id { get; set; }
        ///<summary>
        /// 0：企业，1：人才资源库，2：成果转化，3项目类型
        ///</summary>
        public int type { get; set; }
        ///<summary>
        /// 标签，名称
        ///</summary>
        [MaxLength(20), Required]
        public string label { get; set; }

        ///<summary>
        /// 数量
        ///</summary>
        public int quantity { get; set; }

        public int order { get; set; }

        /// <summary>
        ///状态: 1：禁用  0启用(默认为0)
        /// </summary>
        public bool disabled { get; set; }

        /// <summary>
        /// 创建人
        /// </summary>
        public int msCreatorId { get; set; }
        /// <summary>
        /// 更新人
        /// </summary>
        public int msLastUpdatorId { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }

    }
}