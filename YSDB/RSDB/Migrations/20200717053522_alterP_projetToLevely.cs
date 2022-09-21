using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterP_projetToLevely : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_project_group");

            migrationBuilder.DropColumn(
                name: "deptId",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "deptIds",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "groupId",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "reportId",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "workshopIds",
                table: "p_project");

            migrationBuilder.AddColumn<bool>(
                name: "hasChild",
                table: "p_project",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<int>(
                name: "parentId",
                table: "p_project",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "hasChild",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "parentId",
                table: "p_project");

            migrationBuilder.AddColumn<int>(
                name: "deptId",
                table: "p_project",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "deptIds",
                table: "p_project",
                maxLength: 50,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "groupId",
                table: "p_project",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "reportId",
                table: "p_project",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "workshopIds",
                table: "p_project",
                maxLength: 100,
                nullable: true);

            migrationBuilder.CreateTable(
                name: "p_project_group",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    beginDate = table.Column<DateTime>(nullable: true),
                    beginYear = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    deptName = table.Column<string>(maxLength: 200, nullable: true),
                    endDate = table.Column<DateTime>(nullable: true),
                    endYear = table.Column<int>(nullable: true),
                    gname = table.Column<string>(maxLength: 200, nullable: true),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    masterENumber = table.Column<string>(maxLength: 30, nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    processSection = table.Column<string>(maxLength: 200, nullable: true),
                    productLine = table.Column<string>(maxLength: 200, nullable: true),
                    rdDeptId = table.Column<int>(nullable: true),
                    rdIndex = table.Column<int>(nullable: false),
                    rdNumber = table.Column<string>(maxLength: 50, nullable: true),
                    workshop = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_project_group", x => x.id);
                });
        }
    }
}
