using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addT_beianSourceProjectIdCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "remark",
                table: "t_beian",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "sourceProjectId",
                table: "t_beian",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "remark",
                table: "t_beian");

            migrationBuilder.DropColumn(
                name: "sourceProjectId",
                table: "t_beian");
        }
    }
}
