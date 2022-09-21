using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createP_project_groupTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "p_project_group",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    gname = table.Column<string>(maxLength: 200, nullable: true),
                    rdIndex = table.Column<int>(nullable: false),
                    beginDate = table.Column<DateTime>(nullable: false),
                    endDate = table.Column<DateTime>(nullable: false),
                    beginYear = table.Column<int>(nullable: false),
                    endYear = table.Column<int>(nullable: false),
                    masterENumber = table.Column<string>(maxLength: 30, nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    rdDeptId = table.Column<int>(nullable: true),
                    rdNumber = table.Column<string>(maxLength: 50, nullable: true),
                    deptName = table.Column<string>(maxLength: 200, nullable: true),
                    workshop = table.Column<string>(maxLength: 200, nullable: true),
                    productLine = table.Column<string>(maxLength: 200, nullable: true),
                    processSection = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_project_group", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_project_group");
        }
    }
}
