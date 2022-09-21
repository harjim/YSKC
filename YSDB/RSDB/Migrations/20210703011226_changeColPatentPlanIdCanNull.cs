using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class changeColPatentPlanIdCanNull : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "patentPlanId",
                table: "p_patent_file",
                nullable: true,
                oldClrType: typeof(int));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "patentPlanId",
                table: "p_patent_file",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);
        }
    }
}
