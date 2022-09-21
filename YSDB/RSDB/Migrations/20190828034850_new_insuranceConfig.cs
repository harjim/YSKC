using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class new_insuranceConfig : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "title",
                table: "employee",
                maxLength: 50,
                nullable: true);

            migrationBuilder.CreateTable(
                name: "insuranceConfig",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    endowment = table.Column<decimal>(nullable: false),
                    medical = table.Column<decimal>(nullable: false),
                    unemployment = table.Column<decimal>(nullable: false),
                    injury = table.Column<decimal>(nullable: false),
                    maternity = table.Column<decimal>(nullable: false),
                    house = table.Column<decimal>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    updatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_insuranceConfig", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "insuranceConfig");

            migrationBuilder.DropColumn(
                name: "title",
                table: "employee");
        }
    }
}
